package com.dodo.controlad.facebook

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.dodo.controlad.R
import com.facebook.ads.*


object ControlAdFacebook {

    fun initFacebookAds(context: Context, idInterstitialFacebook: String, showInterstitialAdsFacebook: ShowInterstitialAdsFacebook){
        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(context);
        val interstitialAd = InterstitialAd(context,idInterstitialFacebook)
        AdSettings.addTestDevice("5e6f5705-ca33-4e29-a9ee-7465703e0c88")
        val dialog = Dialog(context, R.style.DialogFragmentTheme)
        dialog.setContentView(R.layout.dialog_loading_ads_fullscreen)
        dialog.show()

        val interstitialAdListener = object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
            }

            override fun onInterstitialDismissed(ad: Ad) {
               showInterstitialAdsFacebook.onCloseInterstitialAdsFacebook()
            }

            override fun onError(ad: Ad?, adError: AdError) {
                showInterstitialAdsFacebook.onCloseInterstitialAdsFacebook()
            }

            override fun onAdLoaded(ad: Ad) {
               showInterstitialAdsFacebook.onLoadedInterstitialAdsFacebook()
                interstitialAd.show()
                dialog.dismiss()
            }

            override fun onAdClicked(ad: Ad) {

            }

            override fun onLoggingImpression(ad: Ad) {

            }
        }
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build())
    }


}