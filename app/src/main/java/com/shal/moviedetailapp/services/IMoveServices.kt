package com.shal.moviedetailapp.services

import com.shal.moviedetailapp.models.Movie
import com.shal.moviedetailapp.models.MovieDetail
import com.shal.moviedetailapp.models.SearchMovies
import retrofit2.Call
import retrofit2.http.*


interface IMoveServices {

    @GET("?apikey=306a33ca")
    fun getMoviesFromTitle(@Query("s") title: String): Call<SearchMovies>

    @GET("?apikey=306a33ca")
    fun getMovieDetailById(@Query("i") i: String): Call<MovieDetail>

}