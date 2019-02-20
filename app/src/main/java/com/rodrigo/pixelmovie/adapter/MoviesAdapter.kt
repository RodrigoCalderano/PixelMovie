package com.rodrigo.pixelmovie.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.view.View
import android.widget.LinearLayout
import com.rodrigo.pixelmovie.R
import com.rodrigo.pixelmovie.model.Movie
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_movie.view.*


class MoviesAdapter(private val movies: List<Movie>, private val rowLayout: Int, private val context: Context, val onClick: (Movie) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    override fun getItemCount(): Int {
        return movies.size
    }


    class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup,
                           viewType: Int): MoviesAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        val view = holder.itemView

        with(view){
            title.text = movies[position].title
            subtitle .text = movies[position].releaseDate
            description.text = movies[position].overview
            rating.text = movies[position].voteAverage.toString()

            setOnClickListener { onClick(movie) }
        }
    }

}