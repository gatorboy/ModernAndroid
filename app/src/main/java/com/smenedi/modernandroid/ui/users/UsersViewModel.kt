package com.smenedi.modernandroid.ui.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smenedi.modernandroid.domain.User
import com.smenedi.modernandroid.repository.GitRepository
import com.smenedi.modernandroid.repository.Result
import com.smenedi.modernandroid.repository.Status

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: GitRepository = GitRepository(viewModelScope, application.applicationContext)

    private val usersResult = repository.loadUsers("Java")
    private val _users = MediatorLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    init {
        _users.addSource(usersResult) {
            when (it) {
                is Result.Loading -> {
                    _users.value = it.data
                    if (it.data == null || it.data.isEmpty()) {
                        _status.value = Status.LOADING
                    } else {
                        _status.value = Status.DONE
                    }
                }
                is Result.Success -> {
                    _users.value = it.data
                    _status.value = Status.DONE
                }

                is Result.Error -> {
                    _users.value = it.data
                    if (it.data == null || it.data.isEmpty()) {
                        _status.value = Status.ERROR
                    } else {
                        _status.value = Status.DONE
                    }
                }
            }
        }
    }

    private val _status = MutableLiveData<Status>()
    val status
        get() = _status

    private val _navigateToUserRepo = MutableLiveData<String>()
    /**
     * If this is non-null, immediately navigate to [UserReposFragment] and call [doneNavigating]
     */
    val navigateToUserRepo: LiveData<String>
        get() = _navigateToUserRepo


    fun onUserClicked(username: String) {
        _navigateToUserRepo.value = username
    }

    fun onUserReposNavigated() {
        _navigateToUserRepo.value = null
    }
}
