package com.smenedi.modernandroid.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smenedi.modernandroid.databinding.CellHeaderLayoutBinding
import com.smenedi.modernandroid.databinding.CellUsersLayoutBinding
import com.smenedi.modernandroid.domain.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class UsersAdapter(private val scope: CoroutineScope, private val userClickListener: UserClickListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(DataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> UserViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                val data = getItem(position) as DataItem.UserItem
                holder.bind(userClickListener, data.user)
            }
            is HeaderViewHolder -> {
                val data = getItem(position) as DataItem.HeaderItem
                holder.bind(data.header)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.HeaderItem -> ITEM_VIEW_TYPE_HEADER
            is DataItem.UserItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class UserViewHolder private constructor(private val binding: CellUsersLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userClickListener: UserClickListener, user: User) {
            binding.user = user
            binding.userClickListener = userClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val binding = CellUsersLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return UserViewHolder(binding)
            }
        }
    }

    class HeaderViewHolder private constructor(private val binding: CellHeaderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: String) {
            binding.headerString = header
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val binding = CellHeaderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

    fun addHeaderAndSubmitList(list: List<User>?) {
        scope.launch(Dispatchers.IO) {
            list?.let {
                val items = it.sortedBy { it.username.toUpperCase() }.groupBy({ user -> user.username.toUpperCase().first() }, { user -> user })
                    .flatMap { mapEntry ->
                        listOf(DataItem.HeaderItem(mapEntry.key.toString())) + mapEntry.value.map {
                            DataItem.UserItem(
                                it
                            )
                        }
                    }

                withContext(Dispatchers.Main) {
                    submitList(items)
                }
            }

        }
    }
}

class DataDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem //kotlin compares each var in it
    }
}

class UserClickListener(val clickListener: (userId: String) -> Unit) {
    fun onClick(user: User) = clickListener(user.username)
}

sealed class DataItem {
    abstract var id: Long

    data class UserItem(val user: User) : DataItem() {
        //TODO change the id to Long in database
        override var id: Long = user.id
    }

    data class HeaderItem(val header: String) : DataItem() {
        override var id: Long = Long.MIN_VALUE
    }
}
