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
                    val halfAndFullTimeScoresInOdds = docs.getString("HalfAndFullTimeScoresInOdds")
                    val matchWon = docs.getBoolean("matchWon")
                    val odds = docs.getString("odds")
                    val halfTimeScore = docs.getString("HalfTimeScore")
                    val fullTimeScore = docs.getString("FullTimeScore")
                    val isMatchPlayed = docs.getBoolean("isMatchPlayed")


                    val vipMatchesItem = VipMatchesItem(
                        leagueLogo!!, leagueName!!, date!!, teamOne!!,
                        teamTwo!!, halfAndFullTimeScoresInOdds!!,
                        matchWon!!, odds!!, halfTimeScore!!, fullTimeScore!!,
                        isMatchPlayed!!
                    )
                    listData.add(vipMatchesItem)
                }
                mutableData.value = listData
            }
        return mutableData
    }


}