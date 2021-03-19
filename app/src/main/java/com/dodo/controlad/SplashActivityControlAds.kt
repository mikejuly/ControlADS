package com.dodo.controlad

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.dodo.controlad.admob.*
import com.dodo.controlad.facebook.ControlAdFacebook

class SplashActivityControlAds : Activity(), ShowOpenAdsAdmobListener {

    private lateinit var appOpenManager: AppOpenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init ads
        ControlAdsAdmob.initAdmob(this)

        // Init open ads
        appOpenManager = AppOpenManager(application as Application)
        appOpenManager.fetchAd(this.getString(R.string.admob_id_ads_open_test), this)

        // Init ads Reward Admob...
        RewardAdAdmob.loadRewardAdAdmob(this, getString(R.string.id_admob_reward_test))
        // Init ads Full Admob...
        InterstitialAdAdmob.initAdAdmob(this, this.getString(R.string.admob_id_interstitial_test))

        ControlAdFacebook.initFacebookAds(this,getString(R.string.id_interstitial_facebook_test))
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