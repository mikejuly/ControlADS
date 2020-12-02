package com.dodo.controlad.admob

import android.content.Context
import com.google.android.gms.ads.MobileAds

object ControlAdsAdmob {
   fun initAdmob(context: Context) {
        MobileAds.initialize(context){}
        }
    }
