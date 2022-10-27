package com.ionix.megamovies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ionix.megamovies.data.entities.GenreMovieList
import com.ionix.megamovies.databinding.ItemCarouselBinding

class CarouselAdapter (private val handler: MovieHandler) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>(),
    MovieAdapter.OnMovieClickListener {


    private val items = mutableListOf<GenreMovieList>()

    fun setItems(items: List<GenreMovieList>){
        this.items.clear()
        this.items.addAll(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding: ItemCarouselBinding = ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    override fun onClickMovie(id: String) {
        handler.onClickMovie(id)
    }

    class CarouselViewHolder(private val itemBinding: ItemCarouselBinding, private val listener: MovieAdapter.OnMovieClickListener) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: GenreMovieList) {
            itemBinding.tvTitle.text = item.genre.value
            val movieAdapter = MovieAdapter(listener)
            movieAdapter.setItems(item.movies)
            itemBinding.rvMovies.layoutManager = LinearLayoutManager(itemBinding.root.context, RecyclerView.HORIZONTAL, false)
            itemBinding.rvMovies.adapter = movieAdapter
        }

    }

    interface MovieHandler{
        fun onClickMovie(idMovie: String)
    }

}