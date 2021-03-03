@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

package com.dodo.controlad.common

import android.content.Context
import android.net.ConnectivityManager


object CheckUtility {
    fun isNetworkAvailable(activity: Context): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}