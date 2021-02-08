package com.dodo.controlad

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.dodo.controlad.admob.AppOpenManager
import com.dodo.controlad.admob.ControlAdsAdmob
import com.dodo.controlad.admob.ShowOpenAdsAdmobListener
import com.google.android.gms.ads.MobileAds

class SplashActivityControlAds : Activity() , ShowOpenAdsAdmobListener{

    private lateinit var appOpenManager : AppOpenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ControlAdsAdmob.initAdmob(this)
         appOpenManager = AppOpenManager(application as Application)
        appOpenManager.fetchAd(this.getString(R.string.admob_id_ads_open), this)

    }

    override fun onLoadedAdsOpenApp() {

    }

    override fun onLoadFailAdsOpenApp() {
        val intent = Intent(this, MainActivityControlAds::class.java)
        startActivity(intent)
        finish()
    }

    override fun onShowAdsOpenAppDismissed() {
        val intent = Intent(this, MainActivityControlAds::class.java)
        startActivity(intent)
        finish()
    }
}