package com.dodo.controlad.facebook

import android.content.Context
import com.dodo.controlad.common.Common
import com.facebook.ads.*


object ControlAdFacebook {

    private var interstitialAd: InterstitialAd? = null
    private lateinit var idInterstitialFacebookTemp: String


    fun initFacebookAds(context: Context, idInterstitialFacebook: String) {
        AudienceNetworkAds.initialize(context);
        interstitialAd = InterstitialAd(context, idInterstitialFacebook)
        idInterstitialFacebookTemp = idInterstitialFacebook
        AdSettings.addTestDevice("6b9d6824-945d-4ada-9981-c0a85df513e2")

        interstitialAd!!.loadAd()

    }

    fun showFacebookAds(context: Context, showInterstitialAdsFacebook: ShowInterstitialAdsFacebook) {
        if (Common.checkTimeShowAdsFacebook(context, Common.TYPE_ADS_FACEBOOK_INTERSTITIAL)) {

            val interstitialAdListener = object : InterstitialAdListener {
                override fun onInterstitialDisplayed(ad: Ad) {
                }

                override fun onInterstitialDismissed(ad: Ad) {
                    initFacebookAds(context, idInterstitialFacebookTemp)
                    showInterstitialAdsFacebook.onCloseInterstitialAdsFacebook()
                }

                override fun onError(ad: Ad?, adError: AdError) {
                    showInterstitialAdsFacebook.onLoadFailInterstitialAdsFacebook()
                }

                override fun onAdLoaded(ad: Ad) {
                    showInterstitialAdsFacebook.onLoadedInterstitialAdsFacebook()
                }

                override fun onAdClicked(ad: Ad) {

                }

                override fun onLoggingImpression(ad: Ad) {

                }
            }
            if (interstitialAd == null || !interstitialAd!!.isAdLoaded) {
                showInterstitialAdsFacebook.onInterstitialAdsFacebookNotShow()
                return
            }
            if (interstitialAd!!.isAdInvalidated) {
                showInterstitialAdsFacebook.onInterstitialAdsFacebookNotShow()
                return
            }
            interstitialAd!!.buildLoadAdConfig().withAdListener(interstitialAdListener).build()
            interstitialAd!!.show()
        } else {
            showInterstitialAdsFacebook.onInterstitialAdsFacebookNotShow()
        }

    }
}