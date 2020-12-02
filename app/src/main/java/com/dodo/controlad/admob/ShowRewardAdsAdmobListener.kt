package com.dodo.controlad.admob

import com.google.android.gms.ads.rewarded.RewardItem

interface ShowRewardAdsAdmobListener  {
    fun onRewardAdsAdmobLoadFail()
    fun onRewardAdsAdmodClose()
    fun onAdsAdmobRewarded(rewardItem: Int)

}