package com.smenedi.modernandroid.domain

import com.smenedi.modernandroid.util.smartTruncate

data class User(
    val id: Long,
    val username: String,
    val avatarUrl: String
)

data class DatabaseRepo(
    val id: Long,
    val owner: String,
    val name: String,
    val desc: String,
    val stars: Double,
    val forks: Double,
    val watchers: Double
) {

    /**
     * Short description is used for displaying truncated descriptions in the UI
     */
    val shortDescription: String
        get() = desc.smartTruncate(200)
}
