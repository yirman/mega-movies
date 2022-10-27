package com.ionix.megamovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ionix.megamovies.data.entities.Genre
import com.ionix.megamovies.data.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun queryAllMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE movieId = :id")
    fun queryMovie(id: String): Flow<Movie>

    @Query("SELECT * FROM movies WHERE genres LIKE '%' || :genre || '%' ORDER BY releaseState DESC")
    fun queryMoviesByGenre(genre: String): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genre: List<Genre>)

    @Query("SELECT * FROM genres")
    fun queryAllGenres(): List<Genre>

    @Query("DELETE FROM movies")
    fun deleteMovies()

    @Query("DELETE FROM genres")
    fun deleteGenres()
}