package com.smenedi.modernandroid.domain

import com.smenedi.modernandroid.util.State
import kotlinx.coroutines.flow.Flow

interface GitRepository {
    fun getUsers(): Flow<State<List<User>>>
}
