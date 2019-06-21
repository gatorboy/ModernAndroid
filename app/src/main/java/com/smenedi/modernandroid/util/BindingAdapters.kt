package com.smenedi.modernandroid.util

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smenedi.modernandroid.R
import com.smenedi.modernandroid.domain.User
import com.smenedi.modernandroid.repository.Status
import com.smenedi.modernandroid.ui.users.UsersAdapter
import timber.log.Timber

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
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

@BindingAdapter("usersListData")
fun RecyclerView.setUsers(users: List<User>?) {
    val adapter = adapter as UsersAdapter
    adapter.addHeaderAndSubmitList(users)
}

@BindingAdapter("usersListStatus")
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
}

