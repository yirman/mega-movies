package com.ionix.megamovies.data.repository


import com.ionix.megamovies.data.local.MovieDao
import com.ionix.megamovies.data.remote.MovieRemoteDataSource
import com.ionix.megamovies.util.localNetworkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
): MovieRepository {

    override fun queryMovies(refreshApi: Boolean) = localNetworkBoundResource(
        query = {
            localDataSource.queryAllMovies()
        },
        shouldFetch = { movieList ->
            movieList.isEmpty() || refreshApi
        },
        fetch = {
            remoteDataSource.requestMovies()
        },
        onFetchSuccess = {
            localDataSource.deleteMovies().also { localDataSource.deleteGenres() }
        },
        saveFetchResult = { resource ->
            resource.data?.movies?.let { movies ->
                localDataSource.insertMovies(movies).also {
                    movies.forEach { movie ->
                        movie.genreList?.let { genres ->
                            localDataSource.insertGenres(genres)
                        }
                    }
                }
            }
        }
    )

    override fun queryMovieDetail(id: String) = localDataSource.queryMovie(id)

    override fun queryAllGenres() = localDataSource.queryAllGenres()

    override fun queryMoviesByGenre(genre: String) = localDataSource.queryMoviesByGenre(genre)
}