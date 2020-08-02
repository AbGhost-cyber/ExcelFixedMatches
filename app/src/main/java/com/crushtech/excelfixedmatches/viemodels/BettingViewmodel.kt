package com.crushtech.excelfixedmatches.viemodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crushtech.excelfixedmatches.app.BettingApplication
import com.crushtech.excelfixedmatches.models.VipMatchesItem
import com.crushtech.excelfixedmatches.repos.BettingRepos
import com.muddzdev.styleabletoastlibrary.StyleableToast

class BettingViewmodel(
    private var repos: BettingRepos,
    private val app: Application
) : AndroidViewModel(app) {
    private var mutableData = MutableLiveData<MutableList<VipMatchesItem>>()

    fun fetchBettingDataFromRepo(path: String): LiveData<MutableList<VipMatchesItem>> {
        if(hasInternetConnection()){
            repos.getBettingDataFromFirebase(path).observeForever { userList ->
                mutableData.value = userList
            }
        }else{
            StyleableToast.makeText(
                app.applicationContext,
                "No internet connection", Toast.LENGTH_SHORT).show()
        }
        return mutableData
    }

    fun refreshData(path:String):LiveData<MutableList<VipMatchesItem>>{
        repos = BettingRepos()
         repos.getBettingDataFromFirebase(path).observeForever {
            mutableData.value = it
        }
        return mutableData
    }





    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<BettingApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    ConnectivityManager.TYPE_VPN -> true
                    else -> false
                }
            }
        }
        return false
    }
}

