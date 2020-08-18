package com.crushtech.excelfixedmatches.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crushtech.excelfixedmatches.R
import com.crushtech.excelfixedmatches.ui.BettingMainActivity
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.free_tips_layout.*

class FreeTipsFragment : Fragment(R.layout.free_tips_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BettingMainActivity).supportActionBar?.show()

        cardViewClickListener(
            listOf(tipsofTheDay, dailyFreeTips)
        )

    }

    private fun cardViewClickListener(mCardViews: List<MaterialCardView>) {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim)
        for (cardView in mCardViews) {
            cardView.setOnClickListener {
                animation.start()
                if (cardView.id == R.id.tipsofTheDay) {
                    val bundle = Bundle().apply {
                        putSerializable("VipTipName", tipsofTheDayTxt.text.toString())
                    }
                    findNavController().navigate(
                        R.id.action_freeTipsFragment_to_vipMatchesFragment,
                        bundle
                    )
                } else if (cardView.id == R.id.dailyFreeTips) {
                    val bundle = Bundle().apply {
                        putSerializable("VipTipName", FreeTipsTxt.text.toString())
                    }

                    findNavController().navigate(
                        R.id.action_freeTipsFragment_to_vipMatchesFragment,
                        bundle
                    )
                }

            }
        }
    }
}