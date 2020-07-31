package com.crushtech.excelfixedmatches.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crushtech.excelfixedmatches.models.VipMatchesItem
import com.google.firebase.firestore.FirebaseFirestore

class BettingRepos {
    fun getCorrectScoresVIPDataFromFB(): LiveData<MutableList<VipMatchesItem>> {
        val mutableData = MutableLiveData<MutableList<VipMatchesItem>>()
        FirebaseFirestore.getInstance().collection("CorrectScoresVIP").get()
            .addOnSuccessListener { result ->
                val listData = mutableListOf<VipMatchesItem>()
                for (docs in result) {
                    val leagueLogo = docs.getString("leagueLogo")
                    val leagueName = docs.getString("leagueName")
                    val date = docs.getString("date")
                    val teamOne = docs.getString("teamOne")
                    val teamTwo = docs.getString("teamTwo")
                    val HalfAndFullTimeScoresInOdds = docs.getString("HalfAndFullTimeScoresInOdds")
                    val matchWon = docs.getBoolean("matchWon")
                    val odds = docs.getString("odds")
                    val HalfTimeScore = docs.getString("HalfTimeScore")
                    val FullTimeScore = docs.getString("FullTimeScore")


                    val vipMatchesItem = VipMatchesItem(
                        leagueLogo!!, leagueName!!, date!!, teamOne!!,
                        teamTwo!!, HalfAndFullTimeScoresInOdds!!,
                        matchWon!!, odds!!, HalfTimeScore!!, FullTimeScore!!
                    )
                    listData.add(vipMatchesItem)
                }
                mutableData.value = listData
            }
        return mutableData
    }


}