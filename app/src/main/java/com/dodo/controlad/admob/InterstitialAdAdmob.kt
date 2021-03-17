package com.dodo.controlad.admob


import android.content.Context
import com.dodo.controlad.common.Common
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.LoadAdError


object InterstitialAdAdmob {

    private lateinit var mInterstitialAds: InterstitialAd

    fun initAdAdmob(context: Context, idAdmobInterstitial: String) {
        mInterstitialAds = InterstitialAd(context)
        mInterstitialAds.adUnitId = idAdmobInterstitial
        loadAdAdmob()
    }


    fun loadAdAdmob() {
        mInterstitialAds.loadAd(AdRequest.Builder().build())
    }

    fun showAdAdmob(
        context: Context,
        showInterstitialAdsAdmobListener: ShowInterstitialAdsAdmobListener
    ) {

        if (Common.checkTimeShowAdsAdmob(context,Common.TYPE_ADS_ADMOB_INTERSTITIAL)) {
                if (mInterstitialAds.isLoaded) {
                    mInterstitialAds.adListener = object : AdListener() {
                        override fun onAdFailedToLoad(p0: LoadAdError?) {
                            super.onAdFailedToLoad(p0)
                            showInterstitialAdsAdmobListener.onLoadFailInterstitialAdsAdmob()
                        }

                        override fun onAdClosed() {
                            super.onAdClosed()
                            showInterstitialAdsAdmobListener.onInterstitialAdsAdmobClose()
                            loadAdAdmob()
                        }

                    }
                    mInterstitialAds.show()
                } else {
                    showInterstitialAdsAdmobListener.onLoadFailInterstitialAdsAdmob()
                }

        }else{
            showInterstitialAdsAdmobListener.onInterstitialAdsNotShow()
        }

    }


}