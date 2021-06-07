package com.shal.moviedetailapp.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shal.moviedetailapp.R
import com.shal.moviedetailapp.models.Movie
import com.shal.moviedetailapp.utils.Utils
import java.io.FileNotFoundException

class MoveListAdapter(val context: Context) :
    RecyclerView.Adapter<MoveListAdapter.MovieVieHolder>() {

    private var dataSet: List<Movie> = mutableListOf()
    val TAG = MoveListAdapter::class.simpleName


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVieHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_movie_row, parent, false)
        return MovieVieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieVieHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class MovieVieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtTitle: TextView = itemView.findViewById(R.id.txt_title)
        private val txtDate: TextView = itemView.findViewById(R.id.txt_date)
        private val imgPoster: ImageView = itemView.findViewById(R.id.image)

        fun bind(movie: Movie) {

            txtTitle.text = movie.Title
            txtDate.text = context.getString(R.string.release_year) + "${movie.Year}"
            try {
                Glide.with(context).load(movie.Poster)
                    .apply(RequestOptions().centerCrop())
                    .into(imgPoster)
            } catch (e: FileNotFoundException) {
                Log.e(TAG, e.message.toString())
            }

            itemView.setOnClickListener {
                val intentSearchActivity = Intent(context, MovieDetailActivity::class.java)
                intentSearchActivity.putExtra(Utils.INTENT_MOVIEW_IMDB_ID, movie.imdbID)
                context.startActivity(intentSearchActivity)
            }

        }
    }


    fun submitList(dataSet: List<Movie>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

}
