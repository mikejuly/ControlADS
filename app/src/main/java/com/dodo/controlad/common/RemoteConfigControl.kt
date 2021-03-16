package com.dodo.controlad.common

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.dodo.controlad.R
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfigControl {



    fun initRemoteConfig(context:Activity){
        Firebase.initialize(context)
        val remoteConfig = Firebase.remoteConfig
        val configSetting = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }

        remoteConfig.setConfigSettingsAsync(configSetting)


        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    Log.d("time show successful", "Config params updated:"+ remoteConfig.getValue("ads_show_after_times").asDouble())
                } else {
                    remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
                }
                Log.d("get time show", "Config params updated:"+ remoteConfig.getValue("ads_show_after_times").asDouble())
            }

    }

    
}