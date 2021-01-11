package com.smenedi.modernandroid.ui.users


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.smenedi.modernandroid.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple user list fragment as the default destination in the navigation.
 */
@AndroidEntryPoint
class UsersFragment : Fragment() {
    private val viewmodel by viewModels<UsersViewModel>()
    lateinit var binding: FragmentUsersBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    /**
     * Sets up the view in the fragment
     */
    private fun setUpViews() {
        with(binding) {
            list.adapter = UserListAdapter(UserClickListener { username: String ->
                viewmodel.onUserClicked(username)
            })
            listViewModel = viewmodel
            lifecycleOwner = viewLifecycleOwner
            list.addItemDecoration(DividerItemDecoration(context, LinearLayout.HORIZONTAL))
        }

        //navigate to user repos
        viewmodel.navigateToUserRepo.observe(viewLifecycleOwner, { username ->
            username?.let {
                this.findNavController().navigate(UsersFragmentDirections.actionUsersFragmentToReposFragment(it))
                viewmodel.onUserReposNavigated()
            }
        })
        
    }
}
