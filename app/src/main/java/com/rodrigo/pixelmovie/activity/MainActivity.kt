package com.rodrigo.pixelmovie.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.rodrigo.pixelmovie.R
import com.rodrigo.pixelmovie.model.MoviesResponse
import com.rodrigo.pixelmovie.network.ApiClient
import com.rodrigo.pixelmovie.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.rodrigo.pixelmovie.adapter.MoviesAdapter
import com.rodrigo.pixelmovie.extensions.API_KEY
import com.rodrigo.pixelmovie.extensions.MY_LANGUAGE
import com.rodrigo.pixelmovie.extensions.MY_REGION
import com.rodrigo.pixelmovie.extensions.isNetworkAvailable
import com.rodrigo.pixelmovie.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createSetup()
    }

    private fun createSetup(){
        movies_recycler_view.layoutManager = LinearLayoutManager(this)

        if(isNetworkAvailable(this)){
            val apiService = ApiClient.client().create(ApiInterface::class.java)
            val call = apiService.getUpcomingMovies(API_KEY, MY_LANGUAGE, MY_REGION)
            call.enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                    if(response.code() == 401){
                        longToast("Ops, aparentemente ocorreu um erro na autencicação :/ Favor avisar ao administrador: code 401")
                        progressUpcoming.visibility = View.GONE
                    }
                    if(response.code() == 404){
                        longToast("Ops, ocorreu um erro na requisição  :/ Favor avisar ao administrador: code 404")
                        progressUpcoming.visibility = View.GONE
                    }
                    if(response.code() == 200){
                        if (response.body() != null){
                            val movies = response.body()!!.results
                            movies_recycler_view.adapter = MoviesAdapter(movies, R.layout.item_movie, applicationContext) { onClickMovie(it) }
                            progressUpcoming.visibility = View.GONE
                        } else{
                            longToast("Ops, aparentemente não há lançamentos cadastrados :/ Favor tentar novamente mais tarde")
                        }
                    } else{
                        longToast("Ops, ocorreu um erro na requisição :/ Favor avisar ao administrador")
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    longToast("Ops, ocorreu um erro na requisição :/ Favor avisar ao administrador")
                    Timber.e(t)
                }
            })
        } else{
            toast("Ops, não consegui acessar internet :/ Tentarei de novo em 10 segundos.")
            progressUpcoming.visibility = View.GONE

            Handler().postDelayed({
                createSetup()
            }, 10000)

        }
    }

    fun onClickMovie(movie: Movie){
        startActivity<MovieDetailsActivity>(
            "movieParam" to movie.id,
            "titleParam" to movie.title
        )
    }
}

