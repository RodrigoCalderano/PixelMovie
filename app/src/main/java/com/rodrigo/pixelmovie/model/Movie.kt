package com.rodrigo.pixelmovie.model

import com.google.gson.annotations.SerializedName


data class Movie(
    @field:SerializedName("poster_path")  var posterPath: String,
    @field:SerializedName("overview") var overview: String,
    @field:SerializedName("release_date") var releaseDate: String,
    @field:SerializedName("id") var id: Int,
    @field:SerializedName("title") var title: String,
    @field:SerializedName("backdrop_path") var backdropPath: String,
    @field:SerializedName("popularity") var popularity: Double,
    @field:SerializedName("vote_average") var voteAverage: Double
)