package com.roman.mynote.ui.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.roman.mynote.R
import com.roman.mynote.databinding.ActivityMainBinding
import com.roman.mynote.utils.TimeManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()

    @Inject lateinit var timeManager: TimeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController


        val currentDate = Date("Thu Sep 21 18:41:55 CST 2023") // Fecha y hora actual
        val pastDate = Date(currentDate.time - 4000)

        val timeAgo = timeManager.getTimeAgo(pastDate)

        Log.d("TAG-TIME",currentDate.toString())
        Log.d("TAG-TIME", timeAgo)
    }
}