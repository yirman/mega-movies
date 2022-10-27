package com.ionix.megamovies.usecase

abstract class UseCase<Params, Response> {
    abstract fun execute(params: Params): Response
}