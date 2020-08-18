package com.crushtech.excelfixedmatches.ui.fragments

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crushtech.excelfixedmatches.R
import com.crushtech.excelfixedmatches.ui.BettingMainActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.betting_home_layout.*

class BettingHomeFragment : Fragment(R.layout.betting_home_layout) {

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
        showSettings()
    }

    private fun showSettings() {
        show_settings.setOnClickListener {
            val dialog = Dialog(requireContext(), R.style.AppTheme)

            dialog.setContentView(R.layout.settings_layout)
            val dismissDialog = dialog.findViewById<ImageView>(R.id.imageView)
            val shareTv = dialog.findViewById<TextView>(R.id.shareAppTv)
            val privacyPolicy = dialog.findViewById<TextView>(R.id.privacyPolicyTv)
            val aboutSub = dialog.findViewById<TextView>(R.id.AboutSubscriptionTv)
            val rateAppTv = dialog.findViewById<TextView>(R.id.rateAppTv)
            val checkOutTips = dialog.findViewById<ExtendedFloatingActionButton>(R.id.checkOutTips)
            checkOutTips.setOnClickListener {
               dialog.dismiss()
                this.findNavController().navigate(R.id.action_bettingHomeFragment_to_vipTipsFragment)
            }
            setUpTextViewsFunctions(listOf(shareTv, privacyPolicy, rateAppTv, aboutSub))

            dismissDialog.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun setUpTextViewsFunctions(textViews: List<TextView>) {

        for (elements in textViews.indices) {
            textViews[elements].setOnClickListener {
                when (elements) {
                    0 -> {
                        setUpShareFunction()
                    }
                    1 -> {
                        showBrowser("http://www.crushtech.unaux.com/privacypolicy/?i=1")
                    }
                    2 -> {
                        rateFunction()
                    }
                    3 -> {
                        showAboutSubscriptionDialog()
                    }
                    else -> {
                        throw IllegalAccessException("required list size is 4")
                    }
                }
            }

        }
    }

    private fun showAboutSubscriptionDialog() {
        val dialog = Dialog(requireContext(), R.style.AppTheme)
        dialog.setContentView(R.layout.about_sub_layout)
        val dismissDialog = dialog.findViewById<ImageView>(R.id.close_sub_dialog)
        val sub_text = dialog.findViewById<TextView>(R.id.sub_text)
        sub_text.makeLinks(Pair("link", View.OnClickListener {
            showBrowser("https://support.google.com/googleplay/answer/7018481?co=GENIE.Platform%3DAndroid&hl=en")
        }))
        dismissDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun setUpShareFunction() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        val appPackageName =
            requireContext().applicationContext.packageName
        val strAppLink: String
        strAppLink = try {
            "https://play.google.com/store/apps/details?id$appPackageName"
        } catch (anfe: ActivityNotFoundException) {
            "https://play.google.com/store/apps/details?id$appPackageName"
        }
        shareIntent.type = "text/link"
        val shareBody =
            "Hey, Check out EXCEL FIXED MATCHES, i use it to get winning odds and betting tips download for free here: \n$strAppLink"
        val shareSub = "APP NAME/TITLE"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(shareIntent, "Share Using"))
    }


    private fun rateFunction() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + requireContext().packageName)
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + requireContext().packageName)
                )
            )
        }
    }

    private fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(p0: View) {
                    Selection.setSelection((p0 as TextView).text as Spannable, 0)
                    p0.invalidate()
                    link.second.onClick(p0)
                }

            }
            val startIndexOfLink = this.text.toString().indexOf(link.first)
            spannableString.setSpan(
                clickableSpan, startIndexOfLink,
                startIndexOfLink + link.first.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.movementMethod = LinkMovementMethod.getInstance()
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }
}