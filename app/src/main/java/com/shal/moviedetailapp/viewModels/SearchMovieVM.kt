package com.shal.moviedetailapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shal.moviedetailapp.models.SearchMovies
import com.shal.moviedetailapp.repositories.SearchRepository

class SearchMovieVM : ViewModel() {

    var searchRepository = SearchRepository()
    private var movieResponseLiveData: LiveData<SearchMovies?>? = null

    init {
        movieResponseLiveData = searchRepository.getMoviesResponseLiveData()
    }

    fun getMoviesResponseLiveData(): LiveData<SearchMovies?>? {
        return movieResponseLiveData
    }

    fun searchMatchingMoviesByTitle(searchMovieTitle: String){
        searchRepository.searchMatchingMoviesByTitle(searchMovieTitle)
    }

}