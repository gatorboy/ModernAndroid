package com.smenedi.modernandroid.ui.users


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.smenedi.modernandroid.databinding.FragmentUsersBinding


/**
 * A simple [Fragment] subclass.
 *
 */
class UsersFragment : Fragment() {

    lateinit var viewModel: UsersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentUsersBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val factory = UsersViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, factory).get(UsersViewModel::class.java)

        binding.lifecycleOwner = this

        val adapter = UsersAdapter(viewModel.viewModelScope, UserClickListener { username: String ->
            viewModel.onUserClicked(username)
        })

        binding.viewModel = viewModel
        binding.users.adapter = adapter

        //navigate to user repos
        viewModel.navigateToUserRepo.observe(this, Observer { username ->
            username?.let {
                this.findNavController().navigate(UsersFragmentDirections.actionUsersFragmentToReposFragment(it))
                viewModel.onUserReposNavigated()
            }
        })

        return binding.root
    }
}
