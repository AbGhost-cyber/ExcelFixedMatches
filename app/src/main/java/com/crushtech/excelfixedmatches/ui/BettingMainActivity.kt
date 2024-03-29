package com.crushtech.excelfixedmatches.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.crushtech.excelfixedmatches.R
import com.crushtech.excelfixedmatches.repos.BettingRepos
import com.crushtech.excelfixedmatches.viemodels.BettingViewModelFactory
import com.crushtech.excelfixedmatches.viemodels.BettingViewmodel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "BettingMainActivity"

class BettingMainActivity : AppCompatActivity() {

    lateinit var bettingViewModel: BettingViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        val bettingRepos = BettingRepos()
        val viewModelProviderFactory = BettingViewModelFactory(bettingRepos, application)
        bettingViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(BettingViewmodel::class.java)

        val navController = Navigation.findNavController(this, R.id.bettingNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        val appBarConfig = AppBarConfiguration(setOf(R.id.bettingHomeFragment))
        setupActionBarWithNavController(bettingNavHostFragment.findNavController(), appBarConfig)

        //subscribe all device to this topic
        FirebaseMessaging.getInstance().subscribeToTopic("bettingtips")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.d(TAG, msg)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = bettingNavHostFragment.findNavController()
        return navController.navigateUp()
    }
}