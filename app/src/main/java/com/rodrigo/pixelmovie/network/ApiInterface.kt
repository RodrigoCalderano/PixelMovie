package com.rodrigo.pixelmovie.network

import com.rodrigo.pixelmovie.model.MovieDetail
import com.rodrigo.pixelmovie.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {
    @GET("movie/upcoming/")
    fun getUpcomingMovies(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("region") region: String): Call<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Call<MovieDetail>
}