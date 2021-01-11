package com.smenedi.modernandroid.data.repository

import com.smenedi.modernandroid.data.database.LocalDataSource
import com.smenedi.modernandroid.data.database.asDomainModel
import com.smenedi.modernandroid.data.network.NetworkDataSource
import com.smenedi.modernandroid.data.network.asDatabaseModel
import com.smenedi.modernandroid.domain.GitRepository
import com.smenedi.modernandroid.domain.User
import com.smenedi.modernandroid.util.State
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Repository for retrieving users data
 */
class GitRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) : GitRepository {

    override fun getUsers(): Flow<State<List<User>>> {
        return flow {
            emit(State.Loading)
            emit(State.Success(localDataSource.fetchUser().map { it.asDomainModel() }.first()))
            val apiResponse = networkDataSource.getUsers()
            if (apiResponse.isSuccessful) {
                val data = apiResponse.body()
                if (data != null) {
                    localDataSource.saveUsers(data.asDatabaseModel())
                }
            }
            emitAll(
                localDataSource.fetchUser().map {
                    State.Success(it.asDomainModel())
                })
        }.catch { e -> emit(State.Error()) }
    }
}
