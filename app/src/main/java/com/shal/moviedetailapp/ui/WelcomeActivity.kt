package com.shal.moviedetailapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.shal.moviedetailapp.R
import com.shal.moviedetailapp.utils.Utils

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var edtSearch: EditText
    lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        edtSearch = findViewById(R.id.edtMovieTitle)
        btnSearch = findViewById(R.id.btnSearch)
        btnSearch.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        val movieTitleToSearch = edtSearch.text
        if (movieTitleToSearch.isNotEmpty()) {
            val intentSearchActivity = Intent(this, SearchActivity::class.java)
            intentSearchActivity.putExtra(Utils.INTENT_MOVIE_NAME_TO_SEARCH, movieTitleToSearch.toString())
            startActivity(intentSearchActivity)
        } else {
            Toast.makeText(this, this.getString(R.string.provide_movie_title), Toast.LENGTH_SHORT)
                .show()
        }
    }
}