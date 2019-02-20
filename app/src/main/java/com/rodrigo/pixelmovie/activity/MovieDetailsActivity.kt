package com.rodrigo.pixelmovie.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.rodrigo.pixelmovie.network.ApiClient
import com.rodrigo.pixelmovie.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.rodrigo.pixelmovie.model.MovieDetail
import com.rodrigo.pixelmovie.R
import com.rodrigo.pixelmovie.extensions.*
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.details_content.*


class MovieDetailsActivity : AppCompatActivity() {

    private val TAG = MovieDetailsActivity::class.java.simpleName
    val id : Int by lazy { intent.getIntExtra("movieParam", 0)}
    val titleReceived : String by lazy { intent.getStringExtra("titleParam")}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        createSetup(savedInstanceState)
    }

    private fun createSetup(savedInstanceState: Bundle?) {
        setupToolbar(R.id.toolbarAC, titleReceived)

        val apiService = ApiClient.client().create(ApiInterface::class.java)
        val call = apiService.getMovieDetails(id, API_KEY, MY_LANGUAGE)
        call.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                val movie = response.body()!!
                fillContent(movie)
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.e(TAG, t.toString() + "deu ruimmm")
            }
        })
    }

    fun fillContent(movieDetailed: MovieDetail){

        if (!movieDetailed.backdrop_path.isNullOrEmpty()){
            appBarImg.loadUrl(BASE_BACKDROP_URL + movieDetailed.backdrop_path)
            appBarLogo.visibility = View.GONE
        }

        //TODO: Layout do conteudo tela detalhe
        //TODO: share + icone de share, zap, link, rating
        //TODO: Tratar erros falta de internet, lista vazia, autorização, conteúdo vazio
        //TODO: Readme, comentar e otimizar

        println(movieDetailed.budget)
        println(movieDetailed.genres[0].genreName)
        println(movieDetailed.homepage)
        println(movieDetailed.imdb_id)
        println(movieDetailed.revenue)
        println(movieDetailed.runtime)
        println(movieDetailed.budget)
    }
}




