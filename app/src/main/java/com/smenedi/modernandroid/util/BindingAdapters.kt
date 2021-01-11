package com.smenedi.modernandroid.util

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smenedi.modernandroid.domain.User
import com.smenedi.modernandroid.domain.UserListItem
import com.smenedi.modernandroid.ui.users.UserListAdapter

@BindingAdapter("goneUnless")
fun goneUnless(view: View, boolean: Boolean) {
    view.visibility = if (boolean) View.VISIBLE else View.GONE
}

@BindingAdapter("gone")
fun gone(view: View, boolean: Boolean) {
    view.visibility = if (boolean) View.GONE else View.VISIBLE
}

@BindingAdapter("users")
fun users(view: RecyclerView, users: List<UserListItem>?) {
    val adapter = view.adapter as UserListAdapter
    if (users.isNullOrEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        adapter.submit(users)
    }
}


@BindingAdapter("useravatar")
fun ImageView.setUserAvatar(user: User?) {
    user?.let {
        val imgUri = it.avatarUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .into(this)
    }
}

/*@BindingAdapter("usersListStatus")
fun ImageView.setStatus(status: Status?) {
    Timber.d("status is ${status.toString()}")
    when(status) {
        Status.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.loading_animation)
        }
        Status.ERROR -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_cloud_off_black_24dp)
        }
        else -> visibility = View.GONE
    }
}*/

