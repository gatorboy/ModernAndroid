package com.smenedi.modernandroid.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A public interface that exposes the [getProperties] method
 */
interface NetworkApi {
    @GET("search/users")
    suspend fun getUsers(@Query("q") search: String): Response<NetworkUsersContainer>
}
