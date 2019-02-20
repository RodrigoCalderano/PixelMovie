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
    var backdrop_path: String
)