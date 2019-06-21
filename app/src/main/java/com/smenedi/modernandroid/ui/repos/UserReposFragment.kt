package com.smenedi.modernandroid.ui.repos


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.smenedi.modernandroid.databinding.FragmentReposBinding


class UserReposFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentReposBinding.inflate(inflater, container, false)
        val args = UserReposFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context, "Argument is ${args.username}", Toast.LENGTH_SHORT).show()
        return binding.root
    }
}
