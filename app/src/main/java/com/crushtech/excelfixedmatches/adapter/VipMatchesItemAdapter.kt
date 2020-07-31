package com.crushtech.excelfixedmatches.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crushtech.excelfixedmatches.R
import com.crushtech.excelfixedmatches.models.VipMatchesItem
import kotlinx.android.synthetic.main.vip_matches_items.view.*

@Suppress("DEPRECATION")
class VipMatchesItemAdapter : RecyclerView.Adapter<VipMatchesItemAdapter.VipMatchesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VipMatchesItemAdapter.VipMatchesViewHolder {
        return VipMatchesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.vip_matches_items, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(
        holder: VipMatchesItemAdapter.VipMatchesViewHolder,
        position: Int
    ) {
        val items = differ.currentList[position]
        holder.itemView.apply {

            Glide.with(context).load(items.leagueLogo).into(leagueLogo)
            league_Name.text = items.leagueName
            teams.text = "${items.teamOne} Vs ${items.teamTwo}"
            when {
                items.matchWon -> {
                    matchState.setImageResource(R.drawable.match_won)
                    datePlusVipNamePlusOddPlusWon.text =
                        "${items.date} HT/FT  \nVIP ${items.odds} Odds WON"
                }
                !items.matchWon -> {
                    matchState.setImageResource(R.drawable.match_lost_icon)
                    datePlusVipNamePlusOddPlusWon.text =
                        "${items.date} HT/FT  \nVIP ${items.odds} Odds LOST"
                }
            }
            odds.text = items.odds
            fixtures_inOdds.text = "HT/FT ${items.HalfAndFullTimeScoresInOdds}"
            HT_FT_scores.text = "${items.HalfTimeScore}\n${items.FullTimeScore}"

        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<VipMatchesItem>() {
        override fun areItemsTheSame(oldItem: VipMatchesItem, newItem: VipMatchesItem): Boolean {
            return oldItem.teamOne == newItem.teamOne
        }

        override fun areContentsTheSame(oldItem: VipMatchesItem, newItem: VipMatchesItem): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)

    inner class VipMatchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}