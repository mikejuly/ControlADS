package com.dodo.controlad

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd

object ControlAdAdmob {

    private lateinit var mInterstitialAds: PublisherInterstitialAd

    fun initAdAdmob(context: Context, idAdmobInterstitial: String) {
        mInterstitialAds = PublisherInterstitialAd(context)
        mInterstitialAds.adUnitId = idAdmobInterstitial
        loadAdAdmob()
    }


     fun loadAdAdmob() {
        mInterstitialAds.loadAd(PublisherAdRequest.Builder().build())
    }

    fun showAdAdmob(showAdsFullAdmobListener: ShowAdsFullAdmobListener) {
        mInterstitialAds.adListener = object : AdListener() {

            override fun onAdFailedToLoad(p0: LoadAdError?) {
                super.onAdFailedToLoad(p0)
                showAdsFullAdmobListener.admobLoadFail()
            }

            override fun onAdClosed() {
                super.onAdClosed()
                showAdsFullAdmobListener.admobClose()
                loadAdAdmob()
            }

        }
        mInterstitialAds.show()
    }


}