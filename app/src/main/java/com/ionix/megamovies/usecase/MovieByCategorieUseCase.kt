package com.ionix.megamovies.usecase

import com.ionix.megamovies.data.entities.GenreMovieList
import com.ionix.megamovies.data.repository.MovieRepositoryImpl
import com.ionix.megamovies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class MovieByCategorieUseCase @Inject constructor(
    private val repository: MovieRepositoryImpl
): UseCase<Boolean, Flow<Resource<List<GenreMovieList>>>>() {

    override fun execute(params: Boolean): Flow<Resource<List<GenreMovieList>>> {

        return repository.queryMovies(params).transform { movieData ->

            when (movieData.status) {
                Resource.Status.SUCCESS -> {
                    val genreList = repository.queryAllGenres()
                    val genreMovieLists = genreList.map { genre ->
                        val movieList = repository.queryMoviesByGenre(genre.value)
                        GenreMovieList(genre, movieList)
                    }
                    emit(Resource.success(genreMovieLists))
                }
                Resource.Status.LOADING -> {
                    emit(Resource.loading())
                }
                Resource.Status.ERROR -> {
                    emit(Resource.error(movieData.message!!))
                }
            }

        }

    }
}