package com.ionix.megamovies.usecase

import com.ionix.megamovies.data.entities.Movie
import com.ionix.megamovies.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(
    private val repository: MovieRepositoryImpl
): UseCase<String, Flow<Movie>>() {


    override fun execute(params: String): Flow<Movie> = repository.queryMovieDetail(params)

}