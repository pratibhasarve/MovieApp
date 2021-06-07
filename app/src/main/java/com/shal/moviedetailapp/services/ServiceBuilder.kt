package com.shal.moviedetailapp.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder {

    companion object {

         private const val baseUrl = "http://www.omdbapi.com"

        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttp = OkHttpClient.Builder().addInterceptor(logger)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
            .build()

        fun <S> buildService(serviceTypes: Class<S>): S {
            return retrofit.create(serviceTypes)
        }
    }
}