package com.smenedi.modernandroid.data.network

import com.smenedi.modernandroid.data.database.DatabaseUser
import com.smenedi.modernandroid.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//Sample response: https://api.github.com/search/users?q=jakewharton
@JsonClass(generateAdapter = true)
data class NetworkUsersContainer (
    @Json(name = "items") val users: List<NetworkUser>
)

@JsonClass(generateAdapter = true)
data class NetworkUser (
    val id: Long,
    @Json(name = "login") val username: String,
    @Json(name = "avatar_url") val avatarUrl: String
)

data class NetworkRepo (
    val id: String,
    @Json(name = "login") val owner: String,
    val name: String,
    @Json(name = "description") val desc: String,
    @Json(name = "stargazers_count") val stars: Double,
    @Json(name = "forks_count") val forks: Double,
    @Json(name = "watchers_count") val watchers: Double
)


fun NetworkUsersContainer.asDomainModel(): List<User> {
    return users.map {
        User(id =  it.id, username = it.username, avatarUrl = it.avatarUrl)
    }
}

fun NetworkUsersContainer.asDatabaseModel(): List<DatabaseUser> {
    return users.map {
        DatabaseUser(id =  it.id, username = it.username, avatarurl = it.avatarUrl)
    }
}

fun NetworkUser.asDomainModel(): User {
    return User(id =  id, username = username, avatarUrl = avatarUrl)
}
