package com.smenedi.modernandroid.domain

import com.smenedi.modernandroid.di.DefaultDispatcher
import com.smenedi.modernandroid.util.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Usecase to get the list of users.
 * Users are grouped by the alphabets
 */
class GetUsersUseCase @Inject constructor(
    private val repository: GitRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<State<List<UserListItem>>> {
        return repository.getUsers()
            .onStart { emit(State.Loading) }
            .catch { e -> emit(State.Error(Exception(e))) }
            .map {
                when (it) {
                    is State.Success -> {
                        val data = it.data.asSequence()
                            .groupBy({ it.username.first() }, { it })
                            .toSortedMap()
                            .flatMap {
                                val list = mutableListOf<UserListItem>(UserListItem.UserCategory(it.key.toString()))
                                val users = it.value.sortedWith(compareBy { it.username }).map {
                                    UserListItem.UserData(it)
                                }
                                list.addAll(users)
                                list
                            }
                        State.Success(data)
                    }
                    is State.Loading -> {
                        State.Loading
                    }
                    is State.Error -> {
                        State.Error(it.exception, it.message)
                    }
                }
            }
            .flowOn(dispatcher)
    }
}

sealed class UserListItem {
    data class UserData(val user: User) : UserListItem()
    data class UserCategory(val category: String) : UserListItem()
}
