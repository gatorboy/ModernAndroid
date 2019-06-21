package com.smenedi.modernandroid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<DatabaseUser>)

    @Query("DELETE FROM user")
    suspend fun clear()
}


@Dao
interface RepoDao {
    @Query("SELECT * FROM repo where repo_id = :id and owner_name = :owner")
    fun getRepo(id: String, owner: String): LiveData<List<DatabaseRepo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<DatabaseRepo>)

    @Query("DELETE FROM repo")
    suspend fun clear()
}
