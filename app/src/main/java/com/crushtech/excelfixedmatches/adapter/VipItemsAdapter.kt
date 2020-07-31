package com.crushtech.excelfixedmatches.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crushtech.excelfixedmatches.R
import com.crushtech.excelfixedmatches.ui.fragments.VipItems
import com.crushtech.excelfixedmatches.ui.fragments.VipTipsFragment
import kotlinx.android.synthetic.main.vip_items_layout.view.*

class VipItemsAdapter(val vipTipsFragment: VipTipsFragment) :
    RecyclerView.Adapter<VipItemsAdapter.VipItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VipItemsViewHolder {
        return VipItemsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vip_items_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: VipItemsViewHolder, position: Int) {
        val vipItems = differ.currentList[position]

        holder.itemView.apply {
            val vipItemsAnimation = AnimationUtils.loadAnimation(
                context,
                android.R.anim.slide_in_left
            )
            Glide.with(context).load(vipItems.image).into(vipImg)
            vipTipName.text = vipItems.name
            setOnClickListener {
                vipItemsAnimation.start()
                val bundle = Bundle().apply {
                    putSerializable("VipTipName", vipItems.name)
                }
                vipTipsFragment.findNavController().navigate(
                    R.id.action_vipTipsFragment_to_vipMatchesFragment,
                    bundle
                )
            }
        }

    }


    private val differCallBack = object : DiffUtil.ItemCallback<VipItems>() {
        override fun areItemsTheSame(oldItem: VipItems, newItem: VipItems): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: VipItems, newItem: VipItems): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    inner class VipItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

