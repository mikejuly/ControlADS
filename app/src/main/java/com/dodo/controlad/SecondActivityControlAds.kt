package com.dodo.controlad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dodo.controlad.admob.InterstitialAdAdmob
import com.dodo.controlad.admob.NativeAdAdmob
import com.dodo.controlad.admob.ShowInterstitialAdsAdmobListener
import com.dodo.controlad.admob.ShowNativeAdsAdmobListener
import kotlinx.android.synthetic.main.second_activity.*

class SecondActivityControlAds : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        NativeAdAdmob.refreshAd(this,framelayout_ads_native2,"#FFFFFF","#000000","#000000",this.getString(R.string.id_admob_native),true, object : ShowNativeAdsAdmobListener {
            override fun onLoadAdsNativeAdmobCompleted() {

            }

            override fun onLoadAdsNativeAdmobFail() {
            
            }

        })

        InterstitialAdAdmob.showAdAdmob(this,showInterstitialAdsAdmobListener = object : ShowInterstitialAdsAdmobListener {
            override fun onLoadFailInterstitialAdsAdmob() {

            }

            override fun onInterstitialAdsAdmobClose() {
              InterstitialAdAdmob.loadAdAdmob()
            }

        })
    }
}