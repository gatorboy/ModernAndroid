package com.smenedi.modernandroid.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.smenedi.modernandroid.database.GitDatabase
import com.smenedi.modernandroid.database.asDomainModel
import com.smenedi.modernandroid.domain.User
import com.smenedi.modernandroid.network.GitApi
import com.smenedi.modernandroid.network.asDatabaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.IOException

class GitRepository(private val scope: CoroutineScope, private val applicationContext: Context) {
    fun loadUsers(query: String): LiveData<Result<List<User>>> = liveData (scope.coroutineContext + Dispatchers.IO) {
        val database = GitDatabase.getInstance(applicationContext)
        try {
            val disposable = emitSource(database.userDao().getAllUsers().asDomainModel().map { Result.Loading(it) })
            val usersContainer = GitApi.retrofitService.getUsers(query)
            val users = usersContainer.asDatabaseModel()

            disposable.dispose()
            database.userDao().insertUsers(users)

            emitSource(database.userDao().getAllUsers().asDomainModel().map { Result.Success(it) })
        } catch (exception: IOException) {
            emitSource(database.userDao().getAllUsers().asDomainModel().map { Result.Error(exception.message ?: "", it) })
        }
    }
}
