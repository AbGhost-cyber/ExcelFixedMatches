package com.crushtech.excelfixedmatches.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.crushtech.excelfixedmatches.R
import com.crushtech.excelfixedmatches.ui.BettingMainActivity

class FreeTipsFragment : Fragment(R.layout.free_tips_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BettingMainActivity).supportActionBar?.show()
    }

}