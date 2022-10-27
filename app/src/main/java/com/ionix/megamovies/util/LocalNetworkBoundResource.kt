package com.ionix.megamovies.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first

@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> localNetworkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline onFetchSuccess: () -> Unit = { },
    crossinline onFetchFailed: (Throwable) -> Unit = { }
) = channelFlow {
    val data = query().first()

    if (shouldFetch(data)) {
        send(Resource.loading())
        try {
            val fetchData = fetch()
            onFetchSuccess()
            saveFetchResult(fetchData)
            query().collect {
                send(Resource.success(it))
            }
        } catch (t: Throwable) {
            onFetchFailed(t)
            query().collect { send(Resource.error(t.message!!, it)) }
        }
    } else {
        query().collect { send(Resource.success(it)) }
    }
}