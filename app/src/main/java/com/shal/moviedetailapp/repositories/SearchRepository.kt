package com.shal.moviedetailapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shal.moviedetailapp.models.SearchMovies
import com.shal.moviedetailapp.services.IMoveServices
import com.shal.moviedetailapp.services.ServiceBuilder
import com.shal.moviedetailapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response


class SearchRepository {

    private val moviesResponseLiveData = MutableLiveData<SearchMovies>()

    fun getMoviesResponseLiveData(): LiveData<SearchMovies?>? {
        return moviesResponseLiveData
    }

    fun searchMatchingMoviesByTitle(searchMovieTitle: String) {
        val iMovieServices = ServiceBuilder.buildService(IMoveServices::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val postIdea = iMovieServices.getMoviesFromTitle(searchMovieTitle)
            postIdea.enqueue(object : retrofit2.Callback<SearchMovies> {
                override fun onResponse(
                    all: Call<SearchMovies>?, response: Response<SearchMovies>?
                ) {
                    val results = response?.body()?.Search
                    Log.i("TAG", "Print the response ${results}")
                    if (results != null) {
                        moviesResponseLiveData.postValue(response.body());
                    }
                }

                override fun onFailure(call: Call<SearchMovies>?, t: Throwable?) {
                    Log.i("TAG", "Print the error message ${t?.message}")
                    moviesResponseLiveData.postValue(null);
                }

            })
        }
    }

}