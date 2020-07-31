package com.crushtech.excelfixedmatches.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.crushtech.excelfixedmatches.R
import com.crushtech.excelfixedmatches.adapter.VipItemsAdapter
import com.crushtech.excelfixedmatches.models.SpacesItemDecoration
import com.crushtech.excelfixedmatches.ui.BettingMainActivity
import kotlinx.android.synthetic.main.vip_tips_layout.*

data class VipItems(val name: String, val image: Int)

class VipTipsFragment : Fragment(R.layout.vip_tips_layout) {
    private var vipItemsList: ArrayList<VipItems>? = null
    private lateinit var vipItemsAdapter: VipItemsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BettingMainActivity).supportActionBar?.show()
        initVipItems()

        vipItemsAdapter = VipItemsAdapter(this)
        vipItemsAdapter.differ.submitList(vipItemsList!!)

        viprecView.apply {
            adapter = vipItemsAdapter
            layoutManager = GridLayoutManager(requireContext(),
                2)
            addItemDecoration(SpacesItemDecoration(10))
        }

    }

    private fun initVipItems() {
        vipItemsList = ArrayList()
        vipItemsList!!.add(
            VipItems(
                "Special VIP",
                R.drawable.special_vip
            )
        )
        vipItemsList!!.add(
            VipItems(
                "Correct Scores",
                R.drawable.correct_scores

            )
        )
        vipItemsList!!.add(
            VipItems(
                "HT/FT Tips",
                R.drawable.halftime_vip_icon

            )
        )
        vipItemsList!!.add(
            VipItems(
                "Basketball VIP",
                R.drawable.basketball_icon

            )
        )
        vipItemsList!!.add(
            VipItems(
                "Correct Scores VIP",
                R.drawable.correct_score_vip_icon

            )
        )
        vipItemsList!!.add(
            VipItems(
                "Over/Under VIP",
                R.drawable.under_over

            )
        )
    }
}