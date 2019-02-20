package com.rodrigo.pixelmovie.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
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
import com.rodrigo.pixelmovie.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createSetup()
    }

    private fun createSetup(){
        movies_recycler_view.layoutManager = LinearLayoutManager(this)

        val apiService = ApiClient.client().create(ApiInterface::class.java)
        val call = apiService.getUpcomingMovies(API_KEY, MY_LANGUAGE, MY_REGION)
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                val movies = response.body()!!.results
                movies_recycler_view.adapter = MoviesAdapter(movies, R.layout.item_movie, applicationContext) { onClickMovie(it) }
                progressUpcoming.visibility = View.GONE
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }

    fun onClickMovie(movie: Movie){
        Log.e("alou", "movie id: " + movie.id)
        startActivity<MovieDetailsActivity>(
            "movieParam" to movie.id,
            "titleParam" to movie.title
        )
    }
}

