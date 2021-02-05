package com.dodo.controlad.admob


import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.dodo.controlad.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.LoadAdError


object  InterstitialAdAdmob {

    private lateinit var mInterstitialAds: InterstitialAd

    fun initAdAdmob(context: Context, idAdmobInterstitial: String) {
        mInterstitialAds = InterstitialAd(context)
        mInterstitialAds.adUnitId = idAdmobInterstitial
        loadAdAdmob()
    }


     fun loadAdAdmob() {
         mInterstitialAds.loadAd(AdRequest.Builder().build())
    }

    fun showAdAdmob(context: Context, showInterstitialAdsAdmobListener: ShowInterstitialAdsAdmobListener) {

        if (!mInterstitialAds.isLoaded){
            return
        }

        val dialog = Dialog(context, R.style.DialogFragmentTheme)
        dialog.setContentView(R.layout.dialog_loading_ads_fullscreen)
        dialog.show()


        Handler(Looper.getMainLooper()).postDelayed({
            if (mInterstitialAds.isLoaded) {
                mInterstitialAds.adListener = object : AdListener() {
                    override fun onAdFailedToLoad(p0: LoadAdError?) {
                        super.onAdFailedToLoad(p0)
                        showInterstitialAdsAdmobListener.onLoadFailInterstitialAdsAdmob()
                        dialog.dismiss()
                    }

                    override fun onAdClosed() {
                        super.onAdClosed()
                        dialog.dismiss()
                        showInterstitialAdsAdmobListener.onInterstitialAdsAdmobClose()
                        loadAdAdmob()
                    }


                }
                mInterstitialAds.show()
            }else{
                showInterstitialAdsAdmobListener.onLoadFailInterstitialAdsAdmob()
                dialog.dismiss()
            }
        },400)


    }


}