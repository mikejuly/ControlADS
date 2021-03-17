package com.dodo.controlad.admob

import android.app.Activity
import com.dodo.controlad.common.RemoteConfigControl
import com.google.android.gms.ads.MobileAds

object ControlAdsAdmob {
   fun initAdmob(activity: Activity) {
        MobileAds.initialize(activity){}
       RemoteConfigControl.initRemoteConfig(activity)

   }
    }
