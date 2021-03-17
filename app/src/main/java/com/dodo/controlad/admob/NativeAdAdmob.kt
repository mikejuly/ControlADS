package com.dodo.controlad.admob

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.dodo.controlad.R
import com.dodo.controlad.common.CheckUtility
import com.dodo.controlad.common.Common
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import kotlinx.android.synthetic.main.admob_native_ads_back_layout.view.*
import kotlinx.android.synthetic.main.admob_native_layout.view.*

object NativeAdAdmob {


    private fun populateUnifiedNativeAdView(
        nativeAd: UnifiedNativeAd,
        adView: UnifiedNativeAdView,
        backgroundAds: String,
        textTitleColor: String,
        textBodyColor: String,
        isMediaView: Boolean
    ) {
        // Set the media view.
        adView.mediaView = adView.findViewById(R.id.ad_media)


        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)


        if (backgroundAds != "") {
            adView.layout_ads_native.setBackgroundColor(Color.parseColor(backgroundAds))
        }


        (adView.headlineView as TextView).setTextColor(Color.parseColor(textTitleColor))
        (adView.bodyView as TextView).setTextColor(Color.parseColor(textBodyColor))

        adView.iconView = adView.findViewById(R.id.ad_app_icon)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline


        if (isMediaView) {
            adView.mediaView.visibility = View.VISIBLE
            adView.mediaView.setMediaContent(nativeAd.mediaContent)
        } else {
            adView.mediaView.visibility = View.GONE
        }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon.drawable
            )
            adView.iconView.visibility = View.VISIBLE
        }

        adView.setNativeAd(nativeAd)


    }


    fun refreshAd(
        context: Activity,
        frameLayoutNative: FrameLayout,
        backgroundAds: String,
        textTitleColor: String,
        textBodyColor: String,
        idAdmobNative: String,
        isMediaView: Boolean,typeAdsNative: Int,
        loadAdsNativeAds: ShowNativeAdsAdmobListener
    ) {
        if (!CheckUtility.isNetworkAvailable(context)){
            loadAdsNativeAds.onLoadAdsNativeAdmobFail()
            return
        }

        if (Common.checkTimeShowAdsAdmob(context, Common.TYPE_ADS_ADMOB_NATIVE)){
            val builder = AdLoader.Builder(context, idAdmobNative)

            if (typeAdsNative == 1){
                builder.forUnifiedNativeAd { unifiedNativeAd ->
                    val adView = context.layoutInflater.inflate(
                        R.layout.admob_native_layout,
                        null
                    ) as UnifiedNativeAdView
                    populateUnifiedNativeAdView(
                        unifiedNativeAd,
                        adView,
                        backgroundAds,
                        textTitleColor,
                        textBodyColor,
                        isMediaView
                    )
                    frameLayoutNative.removeAllViews()
                    frameLayoutNative.addView(adView)
                }
            }else{
                builder.forUnifiedNativeAd { unifiedNativeAd ->
                    val adView = context.layoutInflater.inflate(
                        R.layout.admob_native_layout_second,
                        null
                    ) as UnifiedNativeAdView
                    populateUnifiedNativeAdView(
                        unifiedNativeAd,
                        adView,
                        backgroundAds,
                        textTitleColor,
                        textBodyColor,
                        isMediaView
                    )
                    frameLayoutNative.removeAllViews()
                    frameLayoutNative.addView(adView)
                }
            }


            val adLoader = builder.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    loadAdsNativeAds.onLoadAdsNativeAdmobFail()
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    loadAdsNativeAds.onLoadAdsNativeAdmobCompleted()
                }

            }

            ).build()

            adLoader.loadAd(PublisherAdRequest.Builder().build())
        }else{
            loadAdsNativeAds.onLoadAdsNativeAdmoNotShow()
        }


    }

    private fun populateUnifiedNativeAdView(
        nativeAd: UnifiedNativeAd,
        adView: UnifiedNativeAdView,
        isMediaView: Boolean
    ) {
        adView.mediaView = adView.findViewById(R.id.ad_media)

        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)

        adView.iconView = adView.findViewById(R.id.ad_app_icon)

        (adView.headlineView as TextView).text = nativeAd.headline


        if (isMediaView) {
            adView.mediaView.visibility = View.VISIBLE
            adView.mediaView.setMediaContent(nativeAd.mediaContent)
        } else {
            adView.mediaView.visibility = View.GONE
        }

        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon.drawable
            )
            adView.iconView.visibility = View.VISIBLE
        }

        adView.setNativeAd(nativeAd)


    }

    fun refreshAdNativeBack(
        context: Activity,
        constraintLayoutBack: ConstraintLayout,
        frameLayoutNative: FrameLayout,
        idAdmobNative: String,
        isMediaView: Boolean,
        loadAdsNativeAds: ShowNativeAdsAdmobListener
    ) {

        if (!CheckUtility.isNetworkAvailable(context)){
            loadAdsNativeAds.onLoadAdsNativeAdmobFail()
            return
        }

        val builder = AdLoader.Builder(context, idAdmobNative)


        builder.forUnifiedNativeAd { unifiedNativeAd ->
            val adView = context.layoutInflater.inflate(
                R.layout.admob_native_layout,
                null
            ) as UnifiedNativeAdView
            populateUnifiedNativeAdView(
                unifiedNativeAd,
                adView,
                isMediaView
            )

            frameLayoutNative.removeAllViews()
            frameLayoutNative.addView(adView)
        }

        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                loadAdsNativeAds.onLoadAdsNativeAdmobFail()
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                loadAdsNativeAds.onLoadAdsNativeAdmobCompleted()
            }

        }

        ).build()

        constraintLayoutBack.btn_exit.setOnClickListener {
            context.finish()
        }
        constraintLayoutBack.btn_no_exit.setOnClickListener {
            constraintLayoutBack.visibility = View.GONE
        }
        constraintLayoutBack.rl_transparent.setOnClickListener {
            constraintLayoutBack.visibility = View.GONE
        }
        adLoader.loadAd(PublisherAdRequest.Builder().build())


    }
}