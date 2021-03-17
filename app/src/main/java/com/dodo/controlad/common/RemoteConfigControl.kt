package com.dodo.controlad.common

import android.app.Activity
import com.dodo.controlad.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfigControl {

    fun initRemoteConfig(context: Activity) {
        Firebase.initialize(context)
        val remoteConfig = Firebase.remoteConfig
        val configSetting = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }

        remoteConfig.setConfigSettingsAsync(configSetting)
        remoteConfig.fetchAndActivate().addOnCompleteListener(context) { task ->
            if (!task.isSuccessful) {
                remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
            }

        }

    }


}