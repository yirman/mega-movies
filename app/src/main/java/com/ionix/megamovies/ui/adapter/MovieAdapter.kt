package com.ionix.megamovies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.ionix.megamovies.data.entities.Movie
import com.ionix.megamovies.data.entities.parseDate
import com.ionix.megamovies.databinding.ItemMovieBinding

class MovieAdapter (private val listener: OnMovieClickListener): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val items = mutableListOf<Movie>()

    fun setItems(items: List<Movie>){
        this.items.clear()
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(
        ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        listener
    )


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    class MovieViewHolder(private val itemBinding: ItemMovieBinding, private val listener: OnMovieClickListener): RecyclerView.ViewHolder(itemBinding.root){

        fun bind (movie: Movie){
            itemBinding.tvTitle.text = movie.title
            itemBinding.tvTitle.isSelected = true
            itemBinding.tvDate.text = movie.parseDate()
            Glide.with(itemBinding.root)
                .load(movie.image)
                .placeholder(CircularProgressDrawable(itemBinding.root.context).apply { start() })
                .signature(ObjectKey(movie))
                .into(itemBinding.ivMovie.ivImage)
            itemBinding.root.setOnClickListener {
                listener.onClickMovie(movie.movieId)
            }
        }
    }

    interface OnMovieClickListener{
        fun onClickMovie(id: String)
    }
}