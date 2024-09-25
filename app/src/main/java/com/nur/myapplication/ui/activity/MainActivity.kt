package com.nur.myapplication.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.nur.data.local.prefs.TokenPreferenceHelper
import com.nur.myapplication.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    @Inject
    lateinit var tokenPreferenceHelper: TokenPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        when {
            tokenPreferenceHelper.accessToken == null -> {
                navGraph.setStartDestination(R.id.singInFragment)
            }
            else -> {
                navGraph.setStartDestination(R.id.homeFragment)
            }
        }
        navController.graph = navGraph
    }
}
