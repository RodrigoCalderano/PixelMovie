package com.rodrigo.pixelmovie.model

import com.google.gson.annotations.SerializedName

data class MovieGenres(
    @field:SerializedName("name")  var genreName: String,
    @field:SerializedName("id") var id: Int
)