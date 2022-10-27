package com.ionix.megamovies.data.repository

import com.ionix.megamovies.data.entities.Genre
import com.ionix.megamovies.data.entities.Movie
import com.ionix.megamovies.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun queryMovies(refreshApi: Boolean = false): Flow<Resource<List<Movie>>>

    fun queryMovieDetail(id: String): Flow<Movie>

    fun queryAllGenres(): List<Genre>

    fun queryMoviesByGenre(genre: String): List<Movie>
}