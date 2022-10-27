package com.ionix.megamovies.data.remote

import com.ionix.megamovies.BuildConfig
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET("movies.json?key=${BuildConfig.API_KEY}")
    suspend fun requestMovieList(): Response<MovieListResponse>

}