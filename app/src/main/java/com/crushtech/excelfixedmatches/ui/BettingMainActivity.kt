package com.crushtech.excelfixedmatches.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.crushtech.excelfixedmatches.R
import kotlinx.android.synthetic.main.activity_main.*

class BettingMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.bettingNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navController)

        val appBarConfig = AppBarConfiguration(setOf(R.id.bettingHomeFragment))
        setupActionBarWithNavController(bettingNavHostFragment.findNavController(), appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = bettingNavHostFragment.findNavController()
        return navController.navigateUp()
    }
}