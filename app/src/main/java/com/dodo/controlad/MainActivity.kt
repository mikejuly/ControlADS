package com.dodo.controlad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dodo.controlad.admob.*
import com.google.android.gms.ads.rewarded.RewardItem
import kotlinx.android.synthetic.main.activity_main.*





class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init ads Reward Admob...
        RewardAdAdmob.loadRewardAdAdmob(this, getString(R.string.id_admob_reward))
        // Init ads Full Admob...
        InterstitialAdAdmob.initAdAdmob(this, this.getString(R.string.admob_id_interstitial))


        NativeAdAdmob.refreshAd(this, framelayout_ads_native, "#99FFFF", "#000000", "#000000",
            this.getString(R.string.id_admob_native),false, object : ShowNativeAdsAdmobListener {
                override fun onLoadAdsNativeAdmobCompleted() {
                    Log.e("vao day", "vao")
                }

                override fun onLoadAdsNativeAdmobFail() {

                }
            })


        // Show ads full admob...
        btn_show_ads.setOnClickListener {
            InterstitialAdAdmob.showAdAdmob(this,object : ShowInterstitialAdsAdmobListener {
                override fun onLoadFailInterstitialAdsAdmob() {

                }

                override fun onInterstitialAdsAdmobClose() {

                }
            })
        }


        // Show ads reward admob....
        btn_show_reward.setOnClickListener {
            RewardAdAdmob.showDialogWithTitleRewardAdsAdmob(this,"Nhận phần thưởng", "Bạn muốn xem quảng cáo nhận thưởng không? ","Ok nhận","Không Nhận", object :
                ShowRewardAdsAdmobListener {
                override fun onRewardAdsAdmobLoadFail() {

                }

                override fun onRewardAdsAdmodClose() {
                    Toast.makeText(this@MainActivity, "close ads ",Toast.LENGTH_LONG).show()
                }

                override fun onAdsAdmobRewarded(rewardItem: RewardItem) {
                    Toast.makeText(this@MainActivity, "Nhan thuongw thoi ",Toast.LENGTH_LONG).show()
                }

            })
        }



        // Next activity...
        this.btn_next_activity.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

    }





}