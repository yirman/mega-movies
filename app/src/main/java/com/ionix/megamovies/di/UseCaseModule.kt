package com.ionix.megamovies.di

import com.ionix.megamovies.data.repository.MovieRepositoryImpl
import com.ionix.megamovies.usecase.MovieByCategorieUseCase
import com.ionix.megamovies.usecase.MovieDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideMovieByCategorieUseCase(movieRepository: MovieRepositoryImpl) = MovieByCategorieUseCase(movieRepository)
    @Singleton
    @Provides
    fun provideMovieDetailUseCase(movieRepository: MovieRepositoryImpl) = MovieDetailUseCase(movieRepository)

}