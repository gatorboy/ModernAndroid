package com.smenedi.modernandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.smenedi.modernandroid.R
import com.smenedi.modernandroid.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.myNavHostFragment))

    }

    override fun onSupportNavigateUp(): Boolean {
        Timber.i("on back pressed")
        return findNavController(R.id.myNavHostFragment).navigateUp()
    }
}
