package com.shal.moviedetailapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.shal.moviedetailapp.R
import com.shal.moviedetailapp.utils.Utils
import com.shal.moviedetailapp.viewModels.SearchMovieVM


class SearchActivity : AppCompatActivity() {

    lateinit var moveRecyclerView: RecyclerView
    private val adapter = MoveListAdapter(this)
    private val movieViewModel: SearchMovieVM by viewModels()

    lateinit var txtTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        loadUI()
        val searchMovieTitle = intent.getStringExtra(Utils.INTENT_MOVIE_NAME_TO_SEARCH).toString()
        if (searchMovieTitle != null) {
            loadMovies(searchMovieTitle)
            showMovieData(searchMovieTitle)
        }
    }

    private fun loadUI() {

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        txtTitle = findViewById(R.id.txt_movie_title)

        moveRecyclerView = findViewById(R.id.movie_list)
        moveRecyclerView.adapter = adapter

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadMovies(searchMovieTitle: String) {
        movieViewModel.searchMatchingMoviesByTitle(searchMovieTitle)
    }

    private fun showMovieData(searchMovieTitle: String) {
        txtTitle.text = searchMovieTitle
        movieViewModel.getMoviesResponseLiveData()?.observe(this,
            { searchMovies -> searchMovies?.Search?.let { adapter.submitList(it) } })
    }
}