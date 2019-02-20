package com.rodrigo.pixelmovie.network

import com.rodrigo.pixelmovie.extensions.BASE_URL
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


object ApiClient {

    private var retrofit: Retrofit? = null

    fun client(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }
}