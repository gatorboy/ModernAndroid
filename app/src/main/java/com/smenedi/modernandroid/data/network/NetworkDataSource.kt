package com.smenedi.modernandroid.data.network

import retrofit2.Response
import javax.inject.Inject

/**
 * Data source for retrieving data from the network
 */
class NetworkDataSource @Inject constructor(private val api: NetworkApi) {
    suspend fun getUsers(): Response<NetworkUsersContainer> {
        //TODO: make it searchable from UI
        return api.getUsers("java")
    }
}
