package com.rodrigo.pixelmovie.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rodrigo.pixelmovie.network.ApiClient
import com.rodrigo.pixelmovie.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.rodrigo.pixelmovie.extensions.loadUrl
import com.rodrigo.pixelmovie.model.MovieDetail
import com.rodrigo.pixelmovie.R
import com.rodrigo.pixelmovie.extensions.API_KEY
import com.rodrigo.pixelmovie.extensions.BASE_BACKDROP_URL
import kotlinx.android.synthetic.main.activity_movie_details.*


class MovieDetailsActivity : AppCompatActivity() {

    val id : Int by lazy { intent.getIntExtra("movieParam", 0)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        textView.text = id.toString()


        val apiService = ApiClient.client().create(ApiInterface::class.java)


        val call = apiService.getMovieDetails(id, API_KEY)
        call.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                val teste = response.body()!!.original_title
                textView.text = teste
                imageView.loadUrl(BASE_BACKDROP_URL + response.body()!!.backdrop_path)
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                // Log error here since request failed
                Log.e("tas", t.toString() + "deu ruimmm")
            }
        })
    }

}




