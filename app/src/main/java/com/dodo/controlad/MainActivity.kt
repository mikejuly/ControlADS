package com.dodo.controlad

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*





class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NativeAdAdmob.refreshAd(this, framelayout_ads_native, "#99FFFF", "#000000", "#000000", this.getString(R.string.id_admob_native),false)

        // Init ads Full Admob...
        ControlAdAdmob.initAdAdmob(this, this.getString(R.string.admob_id_interstitial))

        // Show ads admob...
        btn_show_ads.setOnClickListener {
            ControlAdAdmob.showAdAdmob(object : ShowAdsFullAdmobListener {
                override fun admobLoadFail() {

                }

                override fun admobClose() {

                }
            })
        }


        this.btn_next_activity.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

    }





}