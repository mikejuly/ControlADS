package com.dodo.controlad.common

import android.content.Context
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

object Common {

    const val TYPE_ADS_ADMOB_INTERSTITIAL = "type_ads_admob_interstitial"
    const val TYPE_ADS_ADMOB_NATIVE = "type_ads_admob_native"

    private const val PREF_TIME_SHOW_ADS_INTERSTITIAL_ADMOB = "pref_time_show_ads_interstitial_admob"
    private const val PREF_TIME_SHOW_ADS_NATIVE_ADMOB = "pref_time_show_ads_native_admob"

    private const val ADS_ADMOB_INTERSTITIAL_SHOW_AFTER_TIME = "ads_admob_interstitial_show_after_times"
    private const val ADS_ADMOB_NATIVE_SHOW_AFTER_TIME = "ads_admob_native_show_after_times"

    private const val ADS_SHOW_TIME_DEFAULT = 50000L


    private fun setPreferenceTimeShowAds(context: Context, timeShowAds: Long, keyTypeAds: String) {
        val preference = context.getSharedPreferences(keyTypeAds, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putLong(keyTypeAds, timeShowAds)
        editor.apply()
    }

    private fun getPreferenceTimeShowAds(context: Context, keyTypeAds: String): Long {
        val preference = context.getSharedPreferences(keyTypeAds, Context.MODE_PRIVATE)
        return preference.getLong(keyTypeAds, ADS_SHOW_TIME_DEFAULT)
    }

    private fun getTimeSystem(): Long {
        return System.currentTimeMillis()
    }

    fun checkTimeShowAdsAdmob(context: Context, typeAds: String): Boolean {

        val remoteConfig = Firebase.remoteConfig
        when (typeAds) {
            TYPE_ADS_ADMOB_INTERSTITIAL -> {
                return if (getPreferenceTimeShowAds(context, PREF_TIME_SHOW_ADS_INTERSTITIAL_ADMOB) == ADS_SHOW_TIME_DEFAULT) {
                    setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_INTERSTITIAL_ADMOB)
                    true
                } else {
                    if (getPreferenceTimeShowAds(context, PREF_TIME_SHOW_ADS_INTERSTITIAL_ADMOB) + remoteConfig.getLong(ADS_ADMOB_INTERSTITIAL_SHOW_AFTER_TIME) < getTimeSystem()) {
                        setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_INTERSTITIAL_ADMOB)
                        true
                    } else {
                        false
                    }
                }
            }
            TYPE_ADS_ADMOB_NATIVE -> {
                return if (getPreferenceTimeShowAds(context, PREF_TIME_SHOW_ADS_NATIVE_ADMOB) == ADS_SHOW_TIME_DEFAULT) {
                    setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_NATIVE_ADMOB)
                    true
                } else {
                    if (getPreferenceTimeShowAds(context, PREF_TIME_SHOW_ADS_NATIVE_ADMOB) + remoteConfig.getLong(ADS_ADMOB_NATIVE_SHOW_AFTER_TIME) < getTimeSystem()) {
                        setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_NATIVE_ADMOB)
                        true
                    } else {
                        false
                    }
                }
            }
            else -> {
                return false
            }
        }
    }
}