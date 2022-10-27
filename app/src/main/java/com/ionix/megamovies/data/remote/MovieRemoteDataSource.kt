package com.ionix.megamovies.data.remote

import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    var movieService: MovieService
): RemoteDataSource(){

    suspend fun requestMovies() = getResult { movieService.requestMovieList() }

}