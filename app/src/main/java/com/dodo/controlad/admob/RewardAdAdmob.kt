package com.dodo.controlad.admob

import android.app.Activity
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.dodo.controlad.R
import com.example.awesomedialog.*
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.rewarded.RewardItem

import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object RewardAdAdmob {

    private lateinit var mRewardedAd: RewardedAd
    private lateinit var mContext: Activity
    private lateinit var mIdAdsReward: String

    fun loadRewardAdAdmob(context: Activity,idRewardAds: String){

        mIdAdsReward = idRewardAds
        mContext = context
        mRewardedAd = RewardedAd(context,idRewardAds)
        val adLoadCallback = object : RewardedAdLoadCallback(){}
        mRewardedAd.loadAd(PublisherAdRequest.Builder().build(),adLoadCallback)
    }

    fun showDialogRewardAdsAdmob(context: Activity,showRewardListener : ShowRewardAdsAdmobListener){

        AwesomeDialog.build(context)
                .title("Get Reward")
                .body("Do you want see ADS for get the item?")
                .icon(R.drawable.ic_congrts)
                .onPositive("See Ads Get Now!", buttonBackgroundColor = com.example.awesomedialog.R.drawable.layout_rounded_dark_black,textColor = ContextCompat.getColor(context,android.R.color.holo_blue_light)) {
                    showRewardAdAdmob(showRewardListener)
                }
                .onNegative("No, Thank you!",buttonBackgroundColor = com.example.awesomedialog.R.drawable.layout_rounded_white,textColor = ContextCompat.getColor(context,android.R.color.holo_red_light)) {
                }
                .background(com.example.awesomedialog.R.drawable.layout_rounded_white).position(AwesomeDialog.POSITIONS.CENTER)
    }

    fun showDialogWithTitleRewardAdsAdmob(context: Activity, titleReward:String, bodyReward: String, textButtonOK: String, textButtonCancel: String,showRewardListener : ShowRewardAdsAdmobListener){

        val title: String = if (titleReward.isEmpty() )
            "Get Reward"
        else{
            titleReward
        }
        val body: String = if (bodyReward.isEmpty())
            "Do you want see ADS for get the item?"
        else{
            bodyReward
        }

        val txtButtonOK: String = if (textButtonOK.isEmpty())
            "See Ads Get Now!"
        else{
            textButtonOK
        }

        val txtCancel: String = if (textButtonCancel.isEmpty())
            "No, Thank you!"
        else{
            textButtonCancel
        }


        AwesomeDialog.build(context)
                .title(title)
                .body(body)
                .icon(R.drawable.ic_congrts)
                .onPositive(txtButtonOK, buttonBackgroundColor = com.example.awesomedialog.R.drawable.layout_rounded_dark_black,textColor = ContextCompat.getColor(context,android.R.color.holo_blue_light)) {
                    showRewardAdAdmob(showRewardListener)
                }
                .onNegative(txtCancel,buttonBackgroundColor = com.example.awesomedialog.R.drawable.layout_rounded_white,textColor = ContextCompat.getColor(context,android.R.color.holo_red_light)) {
                }
                .background(com.example.awesomedialog.R.drawable.layout_rounded_white).position(AwesomeDialog.POSITIONS.CENTER)
    }


    fun showRewardAdAdmob(showRewardListener : ShowRewardAdsAdmobListener){
        if (mRewardedAd.isLoaded){
            val adCallBack = object : RewardedAdCallback(){
                override fun onUserEarnedReward(p0: RewardItem) {
                   showRewardListener.onAdsAdmobRewarded(p0)
                }

                override fun onRewardedAdClosed() {
                    super.onRewardedAdClosed()
                    showRewardListener.onRewardAdsAdmodClose()
                   mRewardedAd = createNewRewardAdAdmob()

                }

                override fun onRewardedAdFailedToShow(p0: AdError?) {
                    super.onRewardedAdFailedToShow(p0)
                    showRewardListener.onRewardAdsAdmobLoadFail()
                }

            }

            mRewardedAd.show(mContext, adCallBack)
        }else{
            Toast.makeText(mContext,"Ads was not loaded yet",Toast.LENGTH_SHORT).show()
        }

    }

    fun createNewRewardAdAdmob() : RewardedAd{
        val rewardedAd =  RewardedAd(mContext, mIdAdsReward)
        val adLoadCallback = object : RewardedAdLoadCallback(){}
        rewardedAd.loadAd(PublisherAdRequest.Builder().build(),adLoadCallback)
        return rewardedAd
    }

}