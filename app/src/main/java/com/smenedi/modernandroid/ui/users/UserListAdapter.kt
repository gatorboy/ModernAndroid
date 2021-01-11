package com.smenedi.modernandroid.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smenedi.modernandroid.R
import com.smenedi.modernandroid.databinding.CellHeaderLayoutBinding
import com.smenedi.modernandroid.databinding.CellUsersLayoutBinding
import com.smenedi.modernandroid.domain.User
import com.smenedi.modernandroid.domain.UserListItem

class UserListAdapter(private val userClickListener: UserClickListener) : ListAdapter<UserListItem, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<UserListItem>() {
    override fun areItemsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean {
        return (oldItem is UserListItem.UserData && newItem is UserListItem.UserData && oldItem.user.id == newItem.user.id) ||
                (oldItem is UserListItem.UserCategory && newItem is UserListItem.UserCategory && oldItem.category == newItem.category)
    }

    override fun areContentsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.cell_users_layout -> UserDataViewHolder.from(parent)
            R.layout.cell_header_layout -> UserCategoryViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserDataViewHolder -> holder.bind(userClickListener, getItem(position) as UserListItem.UserData)
            is UserCategoryViewHolder -> holder.bind(getItem(position) as UserListItem.UserCategory)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UserListItem.UserCategory -> R.layout.cell_header_layout
            is UserListItem.UserData -> R.layout.cell_users_layout
        }
    }

    fun submit(Users: List<UserListItem>) {
        submitList(Users.toMutableList())
    }
}

/**
 * Viewholder that holds User item
 */
class UserDataViewHolder private constructor(private val binding: CellUsersLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(userClickListener: UserClickListener, userData: UserListItem.UserData) {
        binding.user = userData.user
        binding.userClickListener = userClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): UserDataViewHolder {
            return UserDataViewHolder(CellUsersLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
}

/**
 * Viewholder that holds team name
 */
class UserCategoryViewHolder private constructor(private val binding: CellHeaderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(e: UserListItem.UserCategory) {
        binding.category = e.category
    }

    companion object {
        fun from(parent: ViewGroup): UserCategoryViewHolder {
            return UserCategoryViewHolder(CellHeaderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
}

class UserClickListener(val clickListener: (userId: String) -> Unit) {
    fun onClick(user: User) = clickListener(user.username)
}
