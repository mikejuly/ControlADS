package com.dodo.controlad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.framelayout_ads_native
import kotlinx.android.synthetic.main.second_activity.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        NativeAdAdmob.refreshAd(this,framelayout_ads_native2,"#FFFFFF","#000000","#000000",this.getString(R.string.id_admob_native),true)

        ControlAdAdmob.showAdAdmob(showAdsFullAdmobListener = object : ShowAdsFullAdmobListener{
            override fun admobLoadFail() {

            }

            override fun admobClose() {
              ControlAdAdmob.loadAdAdmob()
            }

        })
    }
}