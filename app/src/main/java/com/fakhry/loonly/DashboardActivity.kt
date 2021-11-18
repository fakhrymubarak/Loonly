package com.fakhry.loonly

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.fakhry.loonly.databinding.ActivityDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarDashboard.toolbar)

        setUpNavDrawer()
        setUpBottomNavBar()
    }

    private fun setUpNavDrawer() {
        val navSide: NavigationView = binding.navSide
        val drawerLayout = binding.drawerLayout
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        navSide.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_wl_movies -> {
                    navController.navigate(R.id.action_nav_movies_to_nav_watchlist_movie)
                }
                R.id.nav_about -> {
                    navController.navigate(R.id.action_nav_movies_to_nav_about)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setUpBottomNavBar() {
        val navBottom: BottomNavigationView = binding.navBottom
        val navController = findNavController(R.id.nav_host_fragment)
        navBottom.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_search -> navController.navigate(R.id.action_nav_movies_to_nav_search_movie)
        }
        return super.onOptionsItemSelected(item)
    }
}