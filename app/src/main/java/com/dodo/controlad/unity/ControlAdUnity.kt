package com.dodo.controlad.unity

import android.app.Activity
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAds.FinishState
import com.unity3d.ads.UnityAds.UnityAdsError


object ControlAdUnity {


    fun  initUnityAd(context: Activity, showInterstitialAdsUnityListener: ShowInterstitialAdsUnityListener){

        // Declare a new listener:
        val myAdsListener = UnityAdsListener(showInterstitialAdsUnityListener)
        // Add the listener to the SDK:
        UnityAds.addListener(myAdsListener)
        // Initialize the SDK:
        UnityAds.initialize(context, "3999675", true)
    }


    // Implement a function to display an ad if the Placement is ready:
    fun displayInterstitialAd(context: Activity) {
        if (UnityAds.isReady("video")) {
            UnityAds.show(context, "video")
        }
    }
    // Implement the IUnityAdsListener interface methods:
     class UnityAdsListener(showInterstitialAdsUnityListener: ShowInterstitialAdsUnityListener) : IUnityAdsListener {
        private val mShowInterstitialAdsUnityListener : ShowInterstitialAdsUnityListener = showInterstitialAdsUnityListener

        override fun onUnityAdsReady(placementId: String) {
            // Implement functionality for an ad being ready to show.

        }

        override fun onUnityAdsStart(placementId: String) {
            // Implement functionality for a user starting to watch an ad.

        }

        override fun onUnityAdsFinish(placementId: String, finishState: FinishState) {
            // Implement conditional logic for each ad completion status:
            when (finishState) {
                FinishState.COMPLETED -> {
                    // Reward the user for watching the ad to completion.
                    mShowInterstitialAdsUnityListener.onInterstitialAdsUnityClose()
                }
                FinishState.SKIPPED -> {
                    // Do not reward the user for skipping the ad.
                    mShowInterstitialAdsUnityListener.onInterstitialAdsUnityClose()
                }
                FinishState.ERROR -> {
                    // Log an error.
                    mShowInterstitialAdsUnityListener.onLoadFailInterstitialAdsUnity()
                }
            }
        }

        override fun onUnityAdsError(error: UnityAdsError, message: String) {
            // Implement functionality for a Unity Ads service error occurring.
        }
    }
}