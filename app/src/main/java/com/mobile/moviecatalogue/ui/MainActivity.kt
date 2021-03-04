package com.mobile.moviecatalogue.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.mobile.moviecatalogue.R
import com.mobile.moviecatalogue.databinding.ActMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.act_main)
        setSupportActionBar(binding.actMainToolbar)
        val host =
            supportFragmentManager.findFragmentById(R.id.act_main_nav_host_frag) as NavHostFragment?
                ?: return

        val navController = host.navController
        val topLevelDestinations = setOf(R.id.movieFragment, R.id.showFragment, R.id.favoriteFragment)
        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations).build()
        setupActionBar(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.actMainBottomNav, host.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.act_main_nav_host_frag).navigateUp(appBarConfiguration)
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfiguration: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopDestination = appBarConfiguration.topLevelDestinations.contains(destination.id)
            if (!isTopDestination) {
                binding.actMainToolbar.setNavigationIcon(R.drawable.svg_arrow_back)
            }
        }
    }

    fun setBottomNavigationVisibility(visibility: Int = View.GONE) {
        binding.actMainBottomNav.visibility = visibility
    }
}