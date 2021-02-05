package com.dodo.controlad.admob

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.dodo.controlad.unity.CheckUtility
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.*


class AppOpenManager(
    context: Application
) : Application.ActivityLifecycleCallbacks,
    LifecycleObserver {

    private var appOpenAd: AppOpenAd? = null
    private lateinit var loadCallBack: AppOpenAd.AppOpenAdLoadCallback
    private var myApplication: Application = context
    private lateinit var currentActivity: Activity
    private var loadTime: Long = 0
    lateinit var fullScreenContentCallback: FullScreenContentCallback

    init {
        this.myApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }


    fun fetchAd(context: Context,
        admobIdOpenApp: String,
        showOpenAdsAdmobListener: ShowOpenAdsAdmobListener, isInEEA: Boolean
    ) {
        if (!CheckUtility.verifyAvailableNetwork(context)){
            showOpenAdsAdmobListener.onLoadFailAdsOpenApp()
            return
        }

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

        val request: AdRequest = getAdRequest(isInEEA)
        AppOpenAd.load(
            myApplication, admobIdOpenApp, request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallBack
        )
    }

    /** Utility method to check if ad was loaded more than n hours ago.  */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    private fun getAdRequest(isInEEA: Boolean): AdRequest {
        if (isInEEA) {
            val extras = Bundle()
            extras.putString("npa", "1")

            return AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build()
        } else {
            return AdRequest.Builder().build()
        }

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