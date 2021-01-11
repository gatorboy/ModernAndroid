package com.smenedi.modernandroid.ui.users

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.smenedi.modernandroid.R
import com.smenedi.modernandroid.domain.GetUsersUseCase
import com.smenedi.modernandroid.domain.UserListItem
import com.smenedi.modernandroid.util.State
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UsersViewModel @ViewModelInject constructor(
    @ApplicationContext val context: Context,
    val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    /**
     * Data holder for progress loading
     */
    val loading: LiveData<Boolean>
        get() = _users.map { it is State.Loading }

    /**
     * Error indicator to show network error or empty state
     */
    val errorVisibility: LiveData<Boolean>
        get() = _users.map { it is State.Success && it.data.isNullOrEmpty() || it is State.Error }

    /**
     * Error text to show network error or empty state
     */
    val errorText: LiveData<String>
        get() = _users.map {
            context.getString(
                if (it is State.Success && it.data.isNullOrEmpty())
                    R.string.empty
                else
                    R.string.error
            )
        }

    /**
     * Icon to show network error or empty state
     */
    val networkIcon: LiveData<Drawable?>
        get() = _users.map {
            ContextCompat.getDrawable(
                context,
                if (it is State.Success && it.data.isNullOrEmpty())
                    R.drawable.empty
                else
                    R.drawable.error
            )
        }

    /**
     * List of users
     */
    private val _users = MutableLiveData<State<List<UserListItem>>>()
    val users: LiveData<List<UserListItem>>
        get() = _users.map { if (it is State.Success) it.data else emptyList() }


    init {
        loadData()
    }

    /**
     * Loads data
     */
    private fun loadData() {
        viewModelScope.launch {
            getUsersUseCase().collect {
                _users.value = it
            }
        }
    }


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
