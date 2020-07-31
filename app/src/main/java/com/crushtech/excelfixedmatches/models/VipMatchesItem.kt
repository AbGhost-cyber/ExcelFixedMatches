package com.crushtech.excelfixedmatches.models

data class VipMatchesItem(
    val leagueLogo:String,
    val leagueName:String,
    val date:String,
    val teamOne:String,
    val teamTwo:String,
    val HalfAndFullTimeScoresInOdds:String,
    val matchWon:Boolean,
    val odds:String,
    val HalfTimeScore:String,
    val FullTimeScore:String
)