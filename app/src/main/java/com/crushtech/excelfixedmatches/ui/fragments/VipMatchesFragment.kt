package com.crushtech.excelfixedmatches.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.crushtech.excelfixedmatches.R
import com.crushtech.excelfixedmatches.adapter.VipItemsAdapter
import com.crushtech.excelfixedmatches.adapter.VipMatchesItemAdapter
import com.crushtech.excelfixedmatches.ui.BettingMainActivity
import com.crushtech.excelfixedmatches.viemodels.BettingViewmodel
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.vip_matches_layout.*

class VipMatchesFragment : Fragment(R.layout.vip_matches_layout) {
    private var vipTipName = ""
    private lateinit var bettingViewmodel: BettingViewmodel
    private lateinit var vipMatchesItemsAdapter: VipMatchesItemAdapter
    private val args: VipMatchesFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vipTipName = args.VipTipName
        (activity as BettingMainActivity).supportActionBar?.title = vipTipName
        bettingViewmodel = (activity as BettingMainActivity).bettingViewModel
        vipMatchesItemsAdapter = VipMatchesItemAdapter()

        vipMatchesRV.apply {
            adapter = vipMatchesItemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        bettingViewmodel.fetchCorrectScoresVIPFromFireBase().observe(viewLifecycleOwner, Observer {
            vipMatchesItemsAdapter.differ.submitList(it)
        })
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh_data -> {
                bettingViewmodel.refreshData()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}