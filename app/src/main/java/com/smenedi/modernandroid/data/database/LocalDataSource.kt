package com.smenedi.modernandroid.data.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Local Data source that handles all the DB operations
 */
class LocalDataSource @Inject constructor(private val db: GitDatabase) {
    /**
     * Return the total stats from the local db
     */
    fun fetchUser(): Flow<List<DatabaseUser>> {
        return db.userDao().getAllUsers()
    }

    /**
     * Save the users to local db
     */
    suspend fun saveUsers(data: List<DatabaseUser>) {
        db.userDao().insertUsers(data)
    }

}
