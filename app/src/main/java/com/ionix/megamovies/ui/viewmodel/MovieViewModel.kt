package com.ionix.megamovies.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionix.megamovies.data.entities.GenreMovieList
import com.ionix.megamovies.data.entities.Movie
import com.ionix.megamovies.usecase.MovieByCategorieUseCase
import com.ionix.megamovies.usecase.MovieDetailUseCase
import com.ionix.megamovies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieByCategorieUseCase: MovieByCategorieUseCase,
    private val movieDetailUseCase: MovieDetailUseCase
) : ViewModel(){

    private val _movies = MutableLiveData<Resource<List<GenreMovieList>>>()
    val movies: LiveData<Resource<List<GenreMovieList>>> = _movies
    private var moviesJob: Job? = null

    private val _movieDetail = MutableLiveData<Movie>()
    val movieDetail: LiveData<Movie> = _movieDetail

    fun queryMovies(refreshApi: Boolean = false) {
        moviesJob?.cancel()
        moviesJob = viewModelScope.launch {
            movieByCategorieUseCase.execute(refreshApi).cancellable().collect { resource ->
                _movies.value = resource
            }
        }
    }

    fun queryMovieDetail(id: String){
        viewModelScope.launch {
            movieDetailUseCase.execute(id).collect{ movie -> _movieDetail.value = movie }
        }
    }
}