package com.rodrigo.pixelmovie.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
import android.content.Intent
import android.net.Uri
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import timber.log.Timber


class MovieDetailsActivity : AppCompatActivity() {

    val id : Int by lazy { intent.getIntExtra("movieParam", 0)}
    val titleReceived : String by lazy { intent.getStringExtra("titleParam")}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        createSetup(savedInstanceState)
    }

    private fun createSetup(savedInstanceState: Bundle?) {
        setupToolbar(R.id.toolbarAC, titleReceived)

        if(isNetworkAvailable(this)) {
            val apiService = ApiClient.client().create(ApiInterface::class.java)
            val call = apiService.getMovieDetails(id, API_KEY, MY_LANGUAGE)
            call.enqueue(object : Callback<MovieDetail> {
                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    if(response.code() == 401){
                        longToast("Ops, aparentemente ocorreu um erro na autencicação :/ Favor avisar ao administrador: code 401")
                    }
                    if(response.code() == 404){
                        longToast("Ops, ocorreu um erro na requisição  :/ Favor avisar ao administrador: code 404")
                    }
                    if(response.code() == 200) {
                        val movie = response.body()!!
                        fillContent(movie)
                    }
                    else{
                        longToast("Ops, ocorreu um erro na requisição :/ Favor avisar ao administrador")
                    }
                }

                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    longToast("Ops, ocorreu um erro na requisição :/ Favor avisar ao administrador")
                    Timber.e(t)
                }
            })
        }
    }

    fun fillContent(movieDetailed: MovieDetail){

        if (!movieDetailed.backdrop_path.isNullOrEmpty()){
            appBarImg.loadUrl(BASE_BACKDROP_URL + movieDetailed.backdrop_path)
            appBarLogo.visibility = View.GONE
        }

        var genres = ""
        for (each in movieDetailed.genres){ genres += " - "  + each.genreName }

        tagline.text = movieDetailed.tagline
        runtime.text = movieDetailed.runtime.toString()
        releaseDetail.text = dateFormatter(movieDetailed.releaseDate)
        voteAverege.text = movieDetailed.voteAverage.toString()
        budget.text = movieDetailed.budget.toString()
        revenue.text = movieDetailed.revenue.toString()
        genresTxt.text = genres
        overviewDetail.text = movieDetailed.overview

        movieSiteContainer.setOnClickListener { openBrowser(movieDetailed.homepage) }
        movieImdbContainer.setOnClickListener { openBrowser(BASE_IMDB_URL + movieDetailed.imdb_id) }
        fab.setOnClickListener { onClickShare(movieDetailed) }
    }

    private fun onClickShare(movieDetailed: MovieDetail){
        val message = "Olá, acabei de ver que " + movieDetailed.title + " lança dia " + dateFormatter(movieDetailed.releaseDate) + ", vamos assistir? =]"
        val whatsappIntent = Intent(Intent.ACTION_SEND)
        whatsappIntent.type = "text/plain"
        whatsappIntent.setPackage("com.whatsapp")
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, message)
        try {
            startActivity(whatsappIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            toast("Ops, não consegui compartilhar :/ Será que você tem Whatsapp instalado?")
        }
    }

    private fun openBrowser(url: String){
        if(!url.isNullOrEmpty()){
            val uris = Uri.parse(url)
            val intents = Intent(Intent.ACTION_VIEW, uris)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            startActivity(intents)
        }else{
            toast("Ops, me parece que esse filme não tem site :/")
        }
    }
}




