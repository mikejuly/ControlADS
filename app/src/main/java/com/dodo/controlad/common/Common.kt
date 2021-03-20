package com.dodo.controlad.common

import android.content.Context
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

object Common {

    const val  MY_PREFS_CONTROL_ADS = "control_ads_prefs";

    const val TYPE_ADS_FACEBOOK_INTERSTITIAL = "type_ads_facebook_interstitial"
    const val TYPE_ADS_FACEBOOK_NATIVE = "type_ads_facebook_native"

    private const val PREF_TIME_SHOW_ADS_INTERSTITIAL_FACEBOOK = "pref_time_show_ads_interstitial_facebook"
    private const val PREF_TIME_SHOW_ADS_NATIVE_FACEBOOK = "pref_time_show_ads_native_facebook"

    private const val REMOTE_CONFIG_ADS_FACEBOOK_INTERSTITIAL_SHOW_AFTER_TIME = "ads_facebook_interstitial_show_after_times"
    private const val REMOTE_CONFIG_ADS_FACEBOOK_NATIVE_SHOW_AFTER_TIME = "ads_facebook_native_show_after_times"


    const val TYPE_ADS_ADMOB_INTERSTITIAL = "type_ads_admob_interstitial"
    const val TYPE_ADS_ADMOB_NATIVE = "type_ads_admob_native"

    private const val PREF_TIME_SHOW_ADS_INTERSTITIAL_ADMOB = "pref_time_show_ads_interstitial_admob"
    private const val PREF_TIME_SHOW_ADS_NATIVE_ADMOB = "pref_time_show_ads_native_admob"

    private const val REMOTE_CONFIG_ADS_ADMOB_INTERSTITIAL_SHOW_AFTER_TIME = "ads_admob_interstitial_show_after_times"
    private const val REMOTE_CONFIG_ADS_ADMOB_NATIVE_SHOW_AFTER_TIME = "ads_admob_native_show_after_times"

    private const val REMOTE_CONFIG_ADS_SHOW_TIME_DEFAULT = 50000L

    private const val PREF_TYPE_SHOW_ADS_IS_ADMOB = "pref_type_ads_is_admob"
    private const val PREF_TIME_SHOW_ADS = "pref_time_show_ads"
    private const val DEFAULT_TIME_ADS_SHOW = 30L
    private const val DEFAULT_TYPE_ADS_SHOW = 1

    private fun setPreferenceTimeShowAds(context: Context, timeShowAds: Long, keyTypeAds: String) {
        val preference = context.getSharedPreferences(MY_PREFS_CONTROL_ADS, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putLong(keyTypeAds, timeShowAds)
        editor.apply()
    }

    fun getPreferenceTimeShowAds(context: Context): Long {
        val preference = context.getSharedPreferences(MY_PREFS_CONTROL_ADS, Context.MODE_PRIVATE)
        return preference.getLong(PREF_TIME_SHOW_ADS, DEFAULT_TIME_ADS_SHOW)
    }

    // Set time so sanh voi time da show ads va cong them time he thong
    fun setPreferenceTimeShowAds(context: Context, timeShowAds: Long) {
        val preference = context.getSharedPreferences(MY_PREFS_CONTROL_ADS, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putLong(PREF_TIME_SHOW_ADS, timeShowAds + getTimeSystem())
        editor.apply()
    }

    fun getPreferenceTypeShowAds(context: Context): Int {
        val preference = context.getSharedPreferences(MY_PREFS_CONTROL_ADS, Context.MODE_PRIVATE)
        return preference.getInt(PREF_TYPE_SHOW_ADS_IS_ADMOB, DEFAULT_TYPE_ADS_SHOW)
    }

    fun setPreferenceTypeShowAds(context: Context, typeShowAds: Int) {
        val preference = context.getSharedPreferences(MY_PREFS_CONTROL_ADS, Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putInt(PREF_TYPE_SHOW_ADS_IS_ADMOB, typeShowAds)
        editor.apply()
    }



    private fun getPreferenceTimeShowAds(context: Context, keyTypeAds: String): Long {
        val preference = context.getSharedPreferences(MY_PREFS_CONTROL_ADS, Context.MODE_PRIVATE)
        return preference.getLong(keyTypeAds, REMOTE_CONFIG_ADS_SHOW_TIME_DEFAULT)
    }

     fun getTimeSystem(): Long {
        return System.currentTimeMillis()
    }

    fun checkTimeShowAdsAdmob(context: Context, typeAds: String): Boolean {

        val remoteConfig = Firebase.remoteConfig
        when (typeAds) {
            TYPE_ADS_ADMOB_INTERSTITIAL -> {
                return if (getPreferenceTimeShowAds(context, PREF_TIME_SHOW_ADS_INTERSTITIAL_ADMOB) == REMOTE_CONFIG_ADS_SHOW_TIME_DEFAULT) {
                    setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_INTERSTITIAL_ADMOB)
                    true
                } else {
                    if (getPreferenceTimeShowAds(
                            context,
                            PREF_TIME_SHOW_ADS_INTERSTITIAL_ADMOB
                        ) + remoteConfig.getLong(REMOTE_CONFIG_ADS_ADMOB_INTERSTITIAL_SHOW_AFTER_TIME) < getTimeSystem()
                    ) {
                        setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_INTERSTITIAL_ADMOB)
                        true
                    } else {
                        false
                    }
                }
            }
            TYPE_ADS_ADMOB_NATIVE -> {
                return if (getPreferenceTimeShowAds(context, PREF_TIME_SHOW_ADS_NATIVE_ADMOB) == REMOTE_CONFIG_ADS_SHOW_TIME_DEFAULT) {
                    setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_NATIVE_ADMOB)
                    true
                } else {
                    if (getPreferenceTimeShowAds(context, PREF_TIME_SHOW_ADS_NATIVE_ADMOB) + remoteConfig.getLong(REMOTE_CONFIG_ADS_ADMOB_NATIVE_SHOW_AFTER_TIME) < getTimeSystem()) {
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


    fun checkTimeShowAdsFacebook(context: Context, typeAds: String): Boolean {
        val remoteConfig = Firebase.remoteConfig
        when (typeAds) {
            TYPE_ADS_FACEBOOK_INTERSTITIAL -> {
                return if (getPreferenceTimeShowAds(context, PREF_TIME_SHOW_ADS_INTERSTITIAL_FACEBOOK) == REMOTE_CONFIG_ADS_SHOW_TIME_DEFAULT) {
                    setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_INTERSTITIAL_FACEBOOK)
                    true
                } else {
                    if (getPreferenceTimeShowAds(
                            context,
                            PREF_TIME_SHOW_ADS_INTERSTITIAL_FACEBOOK
                        ) + remoteConfig.getLong(REMOTE_CONFIG_ADS_FACEBOOK_INTERSTITIAL_SHOW_AFTER_TIME) < getTimeSystem()
                    ) {
                        setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_INTERSTITIAL_FACEBOOK)
                        true
                    } else {
                        false
                    }
                }
            }
            TYPE_ADS_FACEBOOK_NATIVE -> {
                return if (getPreferenceTimeShowAds(context, PREF_TIME_SHOW_ADS_NATIVE_FACEBOOK) == REMOTE_CONFIG_ADS_SHOW_TIME_DEFAULT) {
                    setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_NATIVE_FACEBOOK)
                    true
                } else {
                    if (getPreferenceTimeShowAds(context, PREF_TIME_SHOW_ADS_NATIVE_FACEBOOK) + remoteConfig.getLong(REMOTE_CONFIG_ADS_FACEBOOK_NATIVE_SHOW_AFTER_TIME) < getTimeSystem()) {
                        setPreferenceTimeShowAds(context, getTimeSystem(), PREF_TIME_SHOW_ADS_NATIVE_FACEBOOK)
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