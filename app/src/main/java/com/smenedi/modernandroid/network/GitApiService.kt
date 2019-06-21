package com.smenedi.modernandroid.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getProperties] method
 */
interface GitApiService {
    @GET("search/users")
    suspend fun getUsers(@Query("q") search: String): NetworkUsersContainer
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object GitApi {
    val retrofitService: GitApiService by lazy { retrofit.create(GitApiService::class.java) }
}

