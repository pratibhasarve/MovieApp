package com.shal.moviedetailapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shal.moviedetailapp.R
import com.shal.moviedetailapp.models.MovieDetail
import com.shal.moviedetailapp.services.IMoveServices
import com.shal.moviedetailapp.services.ServiceBuilder
import com.shal.moviedetailapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.io.FileNotFoundException

class MovieDetailActivity : AppCompatActivity() {

    val TAG = MovieDetailActivity::class.simpleName

    lateinit var txtMovieDescription: TextView
    lateinit var imagePoster: ImageView
    lateinit var txtTitle: TextView
    lateinit var txtDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        (supportActionBar)?.setDisplayHomeAsUpEnabled(true);

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                onBackPressed()
            }
        })
        txtMovieDescription = findViewById(R.id.txt_movie_descriptiom)
        txtTitle = findViewById(R.id.txt_title)
        txtDate = findViewById(R.id.txt_date)
        imagePoster = findViewById(R.id.expandedImage)
        val movieId = intent.getStringExtra(Utils.INTENT_MOVIEW_IMDB_ID).toString()
        if (movieId != null) {
            loadMovieDetail(movieId)
        }
    }

    private fun loadMovieDetail(movieId: String) {
        val iMovieServices = ServiceBuilder.buildService(IMoveServices::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val postIdea = iMovieServices.getMovieDetailById(movieId)
            postIdea.enqueue(object : retrofit2.Callback<MovieDetail> {
                override fun onResponse(
                    all: Call<MovieDetail>?, response: Response<MovieDetail>?
                ) {
                    val results = response?.body()
                    Log.i("TAG", "Print the response ${results}")
                    if (results != null) {
                        setData(results)
                    }
                }

                override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                    Log.i("TAG", "Print the error message ${t?.message}")
                }

            })
        }
    }

    fun setData(results: MovieDetail) {
        txtMovieDescription.text = results.Plot
        txtTitle.text = results.Title
        txtDate.text = getString(R.string.release_year) + "${results.Year}"
        try {
            Glide.with(this).load(results.Poster)
                .apply(RequestOptions().centerCrop())
                .into(imagePoster)
        } catch (e: FileNotFoundException) {
            Log.e(TAG, e.message.toString())
        }
    }
}