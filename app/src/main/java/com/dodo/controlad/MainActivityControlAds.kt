package com.dodo.controlad

import android.content.Intent
import android.os.Bundle
import android.service.controls.Control
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dodo.controlad.admob.*
import com.dodo.controlad.common.Common
import com.dodo.controlad.facebook.ControlAdFacebook
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.admob_native_ads_back_layout.*
import com.dodo.controlad.facebook.ShowInterstitialAdsFacebook as ShowInterstitialAdsFacebook1


class MainActivityControlAds : AppCompatActivity() {

    var isloadAdsNativeBack: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inflater = LayoutInflater.from(this)
        inflater.inflate(R.layout.admob_native_ads_back_layout, layout_main)
        layout_ads_native_back.visibility = View.GONE


        // Native admob
        NativeAdAdmob.refreshAd(this, framelayout_ads_native, "", "#000000", "#000000",
            this.getString(R.string.id_admob_native_test), true, 1, object : ShowNativeAdsAdmobListener {
                override fun onLoadAdsNativeAdmobCompleted() {
                    Log.e("vao day", "vao")
                }

                override fun onLoadAdsNativeAdmobFail() {

                }

                override fun onLoadAdsNativeAdmoNotShow() {
                    Toast.makeText(this@MainActivityControlAds, "not enough time show native", Toast.LENGTH_LONG).show()
                }
            })
        // Back ads native admob
        NativeAdAdmob.refreshAdNativeBack(this, layout_ads_native_back, fr_ads_native_back, this.getString(R.string.id_admob_native_test), true, object : ShowNativeAdsAdmobListener {
            override fun onLoadAdsNativeAdmobCompleted() {
                Log.e("vao day", "vao")
                isloadAdsNativeBack = true
            }

            override fun onLoadAdsNativeAdmobFail() {

            }

            override fun onLoadAdsNativeAdmoNotShow() {

            }
        })

        // Show ads full admob...
        btn_show_ads.setOnClickListener {
            InterstitialAdAdmob.showAdAdmob(this, object : ShowInterstitialAdsAdmobListener {
                override fun onLoadFailInterstitialAdsAdmob() {

                }

                override fun onInterstitialAdsAdmobClose() {

                }

                override fun onInterstitialAdsNotShow() {
                    Toast.makeText(this@MainActivityControlAds, "not enough time show", Toast.LENGTH_LONG).show()
                }
            })

        }


        // Show ads reward admob....
        btn_show_reward.setOnClickListener {
            RewardAdAdmob.showDialogWithTitleRewardAdsAdmob(
                this,
                "Nhận phần thưởng",
                "Bạn muốn xem quảng cáo nhận thưởng không? ",
                "Ok nhận",
                "Không Nhận",
                object :
                    ShowRewardAdsAdmobListener {
                    override fun onRewardAdsAdmobLoadFail() {

                    }

                    override fun onRewardAdsAdmodClose() {
                        Toast.makeText(this@MainActivityControlAds, "close ads ", Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onAdsAdmobRewarded(rewardItem: Int) {
                        Toast.makeText(
                            this@MainActivityControlAds,
                            "Nhan thuongw thoi ",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
        }


        // Next activity... show facebook ads
        this.btn_next_activity.setOnClickListener {
            ControlAdFacebook.showFacebookAds(this,object : com.dodo.controlad.facebook.ShowInterstitialAdsFacebook{
                override fun onLoadFailInterstitialAdsFacebook() {
                    val intent = Intent(this@MainActivityControlAds, SecondActivityControlAds::class.java)
                    startActivity(intent)
                }

                override fun onLoadedInterstitialAdsFacebook() {

                }

                override fun onCloseInterstitialAdsFacebook() {
                    val intent = Intent(this@MainActivityControlAds, SecondActivityControlAds::class.java)
                    startActivity(intent)
                }

                override fun onInterstitialAdsFacebookNotShow() {

                }

            })

        }


    }


    override fun onBackPressed() {
        if (isloadAdsNativeBack) {
            layout_ads_native_back.visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }


    }


}