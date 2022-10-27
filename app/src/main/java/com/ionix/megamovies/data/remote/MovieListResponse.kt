package com.ionix.megamovies.data.remote

import com.google.gson.annotations.SerializedName
import com.ionix.megamovies.data.entities.Movie

data class MovieListResponse (

    @SerializedName("items") val movies : List<Movie>,
    @SerializedName("errorMessage") val errorMessage : String
)