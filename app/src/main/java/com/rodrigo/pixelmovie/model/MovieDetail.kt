package com.rodrigo.pixelmovie.model

import com.google.gson.annotations.SerializedName

class MovieDetail(
    @SerializedName("id")
    var id: Int,
    @SerializedName("original_title")
    var original_title: String,
    @SerializedName("original_language")
    var original_language: String,
    @SerializedName("backdrop_path")
    var backdrop_path: String,
    @SerializedName("genres")
    var genres: List<MovieGenres>,
    @SerializedName("title")
    var title: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("runtime")
    var runtime: Long,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("revenue")
    var revenue: Long,
    @SerializedName("budget")
    var budget: Long,
    @SerializedName("imdb_id")
    var imdb_id: String,
    @SerializedName("homepage")
    var homepage: String,
    @SerializedName("tagline")
    var tagline: String,
    @SerializedName("poster_path")
    var posterPath: String
)