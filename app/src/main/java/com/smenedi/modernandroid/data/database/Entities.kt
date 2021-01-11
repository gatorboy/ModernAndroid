package com.smenedi.modernandroid.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.smenedi.modernandroid.domain.User

@Entity(tableName = "user")
data class DatabaseUser(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "user_name") val username: String,
    @ColumnInfo(name = "avatar_url") val avatarurl: String
)

@Entity(
    tableName = "repo",
    primaryKeys = ["repo_id", "owner_name"],
    foreignKeys = [ForeignKey(entity = DatabaseUser::class, parentColumns = arrayOf("id"), childColumns = arrayOf("owner_name"), onDelete = ForeignKey.NO_ACTION)]
)
data class DatabaseRepo(
    @ColumnInfo(name = "repo_id") val id: Long,
    @ColumnInfo(name = "owner_name") val owner: String,
    @ColumnInfo(name = "repo_name") val name: String,
    @ColumnInfo(name = "description") val desc: String,
    @ColumnInfo(name = "stars_count") val stars: Double,
    @ColumnInfo(name = "forks_count") val forks: Double,
    @ColumnInfo(name = "watchers_count") val watchers: Double
)


fun List<DatabaseUser>.asDomainModel(): List<User> {
    return map {  User(id = it.id, username = it.username, avatarUrl = it.avatarurl) } 
}
