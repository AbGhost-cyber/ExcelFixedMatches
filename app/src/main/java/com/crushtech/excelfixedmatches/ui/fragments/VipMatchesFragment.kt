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
import com.crushtech.excelfixedmatches.adapter.VipMatchesItemAdapter
import com.crushtech.excelfixedmatches.models.VipMatchesItem
import com.crushtech.excelfixedmatches.ui.BettingMainActivity
import com.crushtech.excelfixedmatches.viemodels.BettingViewmodel
import kotlinx.android.synthetic.main.vip_matches_layout.*

class VipMatchesFragment : Fragment(R.layout.vip_matches_layout) {
    private var vipTipName = ""
    private lateinit var bettingViewmodel: BettingViewmodel
    private lateinit var vipMatchesItemsAdapter: VipMatchesItemAdapter
    private val args: VipMatchesFragmentArgs by navArgs()
    private lateinit var path: String
    private lateinit var splitPath: List<String>
    private lateinit var sumofSplittedPath: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vipTipName = args.VipTipName
        (activity as BettingMainActivity).supportActionBar?.title = vipTipName
        path = (activity as BettingMainActivity).supportActionBar?.title.toString()

        //removes space between the tips
        sumofSplittedPath = ""
        splitPath = path.split(' ')
        for (paths in splitPath.indices) {
            sumofSplittedPath += splitPath[paths]
        }
        bettingViewmodel = (activity as BettingMainActivity).bettingViewModel
        vipMatchesItemsAdapter = VipMatchesItemAdapter()

        vipMatchesRV.apply {
            adapter = vipMatchesItemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // search depending on app bar title... easy yeah?
        bettingViewmodel.fetchBettingDataFromRepo(sumofSplittedPath)
            .observe(viewLifecycleOwner, Observer {
                updateUI(it)
                vipMatchesItemsAdapter.differ.submitList(it)
            })



        setHasOptionsMenu(true)
    }

    private fun updateUI(vipMatchItems: MutableList<VipMatchesItem>) {
        if (vipMatchItems.isEmpty()) {
            progressivebar.visibility = View.VISIBLE
        } else {
            progressivebar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh_data -> {
                bettingViewmodel.refreshData(sumofSplittedPath)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}