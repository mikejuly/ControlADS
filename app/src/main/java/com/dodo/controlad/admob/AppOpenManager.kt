package com.dodo.controlad.admob

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.dodo.controlad.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.*


class AppOpenManager(
    context: Application,
    showOpenAdsAdmobListenerTemp: ShowOpenAdsAdmobListener
) : Application.ActivityLifecycleCallbacks,
    LifecycleObserver {

    private var appOpenAd: AppOpenAd? = null
    private lateinit var loadCallBack: AppOpenAd.AppOpenAdLoadCallback
    private var myApplication: Application = context
    private lateinit var currentActivity: Activity
    private var loadTime: Long = 0
    private var showOpenAdsAdmobListener: ShowOpenAdsAdmobListener
    lateinit var fullScreenContentCallback: FullScreenContentCallback

    init {
        this.myApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        showOpenAdsAdmobListener = showOpenAdsAdmobListenerTemp
    }


    fun fetchAd() {
        if (isAdAvailable()) {
            return
        }
        loadCallBack = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAppOpenAdLoaded(ad: AppOpenAd) {
                super.onAppOpenAdLoaded(ad)
                appOpenAd = ad
                loadTime = Date().time
                showOpenAdsAdmobListener.onLoadedAdsOpenApp()
                Log.e("Control Ads ", "load ad open app done")
                fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        appOpenAd = null
                        showOpenAdsAdmobListener.onShowAdsOpenAppDismissed()

                    }
                }

                appOpenAd?.show(currentActivity, fullScreenContentCallback)
            }

            override fun onAppOpenAdFailedToLoad(p0: LoadAdError) {
                super.onAppOpenAdFailedToLoad(p0)
                showOpenAdsAdmobListener.onLoadFailAdsOpenApp()
                Log.e("Control Ads ", "load ad open app FAIL")
            }
        }

        val request: AdRequest = getAdRequest()
        AppOpenAd.load(
            myApplication, myApplication.getString(R.string.admob_id_ads_open), request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallBack
        )
    }

    /** Utility method to check if ad was loaded more than n hours ago.  */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    private fun getAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }

    private fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
    }

    // implement Application.ActivityLifecycleCallbacks
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}