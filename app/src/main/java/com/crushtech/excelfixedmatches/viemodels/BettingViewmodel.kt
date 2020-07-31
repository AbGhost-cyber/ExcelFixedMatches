package com.crushtech.excelfixedmatches.viemodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crushtech.excelfixedmatches.models.VipMatchesItem
import com.crushtech.excelfixedmatches.repos.BettingRepos

class BettingViewmodel(
    private var repos: BettingRepos,
    private val app: Application
) : AndroidViewModel(app) {
    private var mutableData = MutableLiveData<MutableList<VipMatchesItem>>()

    fun fetchCorrectScoresVIPFromFireBase(): LiveData<MutableList<VipMatchesItem>> {
        repos.getCorrectScoresVIPDataFromFB().observeForever { userList ->
            mutableData.value = userList
        }
        return mutableData
    }

    fun refreshData(){
        repos = BettingRepos()
        mutableData = repos.getCorrectScoresVIPDataFromFB()
                as MutableLiveData<MutableList<VipMatchesItem>>
    }
}