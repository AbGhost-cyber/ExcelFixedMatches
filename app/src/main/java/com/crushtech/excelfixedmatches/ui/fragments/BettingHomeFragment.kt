package com.crushtech.excelfixedmatches.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crushtech.excelfixedmatches.R
import com.crushtech.excelfixedmatches.ui.BettingMainActivity
import kotlinx.android.synthetic.main.betting_home_layout.*

class BettingHomeFragment :Fragment(R.layout.betting_home_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BettingMainActivity).supportActionBar?.hide()
        val cardViewAnim = AnimationUtils.loadAnimation(
            requireContext(),
            android.R.anim.slide_in_left
        )

        FreeTipsCardView.setOnClickListener {
            FreeTipsCardView.animation = cardViewAnim
            findNavController().navigate(R.id.action_bettingHomeFragment_to_freeTipsFragment)
        }

        vipTipsCardView.setOnClickListener {
            vipTipsCardView.animation = cardViewAnim
            findNavController().navigate(R.id.action_bettingHomeFragment_to_vipTipsFragment)
        }
    }
}