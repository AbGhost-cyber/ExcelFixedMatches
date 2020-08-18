package com.crushtech.excelfixedmatches.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.billingclient.api.*
import com.crushtech.excelfixedmatches.R
import com.crushtech.excelfixedmatches.adapter.VipItemsAdapter
import com.crushtech.excelfixedmatches.models.SpacesItemDecoration
import com.crushtech.excelfixedmatches.ui.BettingMainActivity
import com.muddzdev.styleabletoastlibrary.StyleableToast
import kotlinx.android.synthetic.main.vip_tips_layout.*

data class VipItems(val name: String, val image: Int)

class VipTipsFragment : Fragment(R.layout.vip_tips_layout), PurchasesUpdatedListener,
    PurchaseHistoryResponseListener {
    private val skuList = listOf(
        "special_vip",
        "correct_scores",
        "ht_ft_tips",
        "daily_20_odd",
        "correct_scores_vip",
        "over_under_vip",
        "all_items_sub"
    )
    private lateinit var billingClient: BillingClient
    private var vipItemsList: ArrayList<VipItems>? = null
    private lateinit var vipItemsAdapter: VipItemsAdapter
    private var allItemsOwned = false
    private var itemOneIsOwned = false
    private var itemTwoIsOwned = false
    private var itemThreeIsOwned = false
    private var itemFourIsOwned = false
    private var itemFiveIsOwned = false
    private var itemSixIsOwned = false

    private var skuDetailForItemOne: SkuDetails? = null
    private var skuDetailForItemTwo: SkuDetails? = null
    private var skuDetailForItemThree: SkuDetails? = null
    private var skuDetailForItemFour: SkuDetails? = null
    private var skuDetailForItemFive: SkuDetails? = null
    private var skuDetailForItemSix: SkuDetails? = null
    private var allItemsSkuDetails: SkuDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BettingMainActivity).supportActionBar?.show()

        initVipItems()

        val vipItemsAnimation = AnimationUtils.loadAnimation(
            context,
            android.R.anim.slide_in_left
        )
        vipItemsAdapter = VipItemsAdapter()
        vipItemsAdapter.differ.submitList(vipItemsList!!)

        viprecView.apply {
            adapter = vipItemsAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                2
            )
            addItemDecoration(SpacesItemDecoration(10))
        }

        billingClient = BillingClient
            .newBuilder(requireContext())
            .enablePendingPurchases()
            .setListener(this)
            .build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                showToast("service disconnected", R.style.customToast2)
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    loadProducts()
                    queryInventoryAsync()

                }
            }

        })

        vipItemsAdapter.setOnItemClickListener { vipItems ->
            vipItemsAnimation.start()
            val bundle = Bundle().apply {
                putSerializable("VipTipName", vipItems.name)
            }
            if (vipItems.name.contains("Special", true)) {
                skuDetailForItemOne?.let { checkPurchaseState(itemOneIsOwned, it, bundle) }
            } else if (vipItems.name.startsWith("Correct", true)
                && vipItems.name.endsWith("Scores", true)
            ) {
                skuDetailForItemTwo?.let { checkPurchaseState(itemTwoIsOwned, it, bundle) }
            } else if (vipItems.name.contains("HT FT TIPS", true)) {
                skuDetailForItemThree?.let { checkPurchaseState(itemThreeIsOwned, it, bundle) }
            } else if (vipItems.name.contains("Daily", true)) {
                skuDetailForItemFour?.let { checkPurchaseState(itemFourIsOwned, it, bundle) }
            } else if (vipItems.name.startsWith("Correct", true)
                && vipItems.name.endsWith("VIP", true)
            ) {
                skuDetailForItemFive?.let { checkPurchaseState(itemFiveIsOwned, it, bundle) }
            } else if (vipItems.name.contains("Over", true)) {
                skuDetailForItemSix?.let { checkPurchaseState(itemSixIsOwned, it, bundle) }
            }
        }

        unlockAllItems.setOnClickListener {
            if (!allItemsOwned) {
                val flowParams = allItemsSkuDetails?.let { skuDetails ->
                    BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetails)
                        .build()
                }
                if (flowParams != null) {
                    billingClient.launchBillingFlow(activity as BettingMainActivity, flowParams)
                }
            } else {
                showToast("all items already purchased", R.style.alreadySubbed)
            }
        }
    }

    private fun queryInventoryAsync() {
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)
        billingClient.querySkuDetailsAsync(
            params.build()
        ) { billingResult, skuDetailList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (!JavaUtils.isNullOrEmpty(skuDetailList)) {
                    if (skuDetailList != null) {
                        for (item in skuDetailList.withIndex()) {
                            val skuItem = item.value
                            if (skuItem.sku == "special_vip") {
                                skuDetailForItemOne = skuItem
                            }
                            if (skuItem.sku == "correct_scores") {
                                skuDetailForItemTwo = skuItem
                            }
                            if (skuItem.sku == "ht_ft_tips") {
                                skuDetailForItemThree = skuItem
                            }
                            if (skuItem.sku == "daily_20_odd") {
                                skuDetailForItemFour = skuItem
                            }
                            if (skuItem.sku == "correct_scores_vip") {
                                skuDetailForItemFive = skuItem
                            }
                            if (skuItem.sku == "over_under_vip") {
                                skuDetailForItemSix = skuItem
                            }
                            if (skuItem.sku == "all_items_sub") {
                                allItemsSkuDetails = skuItem
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadProducts() {
        billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.SUBS, this)
    }

    private fun showToast(text: String, style: Int) {
        StyleableToast.makeText(requireContext(), text, style).show()
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
                "HT FT TIPS",
                R.drawable.halftime_vip_icon

            )
        )
        vipItemsList!!.add(
            VipItems(
                "DAILY 20 ODD",
                R.drawable.daily_20
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
                "Over Under VIP",
                R.drawable.under_over

            )
        )
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchaseList: MutableList<Purchase>?
    ) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                for (purchase in purchaseList!!) {
                    acknowledgePurchase(purchase.purchaseToken)
                }
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                showToast(
                    "You've cancelled the Google play billing process...",
                    R.style.customToast2
                )
            }
            else -> {
                showToast("Item not found or Google play billing error...", R.style.customToast2)
            }
        }
    }

    private fun acknowledgePurchase(purchaseToken: String) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchaseToken)
            .build()
        billingClient.acknowledgePurchase(params) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val debugMessage = billingResult.debugMessage
                showToast("Item Purchased", R.style.alreadySubbed)
            }
        }
    }

    override fun onPurchaseHistoryResponse(
        billingResult: BillingResult,
        purchaseHistoryList: MutableList<PurchaseHistoryRecord>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            if (!JavaUtils.isNullOrEmpty(purchaseHistoryList)) {
                for (purchase in purchaseHistoryList!!) {
                    for (items in skuList) {
                        if (purchase.sku == "special_vip") {
                            itemOneIsOwned = true
                        }
                        if (purchase.sku == "correct_scores") {
                            itemTwoIsOwned = true
                        }
                        if (purchase.sku == "ht_ft_tips") {
                            itemThreeIsOwned = true
                        }
                        if (purchase.sku == "daily_20_odd") {
                            itemFourIsOwned = true
                        }
                        if (purchase.sku == "correct_scores_vip") {
                            itemFiveIsOwned = true
                        }
                        if (purchase.sku == "over_under_vip") {
                            itemSixIsOwned = true
                        }
                        if (purchase.sku == "all_items_sub") {
                            itemOneIsOwned = true
                            itemTwoIsOwned = true
                            itemThreeIsOwned = true
                            itemFourIsOwned = true
                            itemFiveIsOwned = true
                            itemSixIsOwned = true
                            allItemsOwned = true
                        }
                    }
                }
            }
        }
    }

    object JavaUtils {
        fun isNullOrEmpty(list: List<*>?): Boolean {
            return list == null || list.isEmpty()
        }
    }

    private fun checkPurchaseState(isPurchased: Boolean, skuDetails: SkuDetails, bundle: Bundle) {
        if (!isPurchased) {
            val flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build()
            billingClient.launchBillingFlow(activity as BettingMainActivity, flowParams)
        } else {
            findNavController().navigate(
                R.id.action_vipTipsFragment_to_vipMatchesFragment,
                bundle
            )
        }
    }
}
