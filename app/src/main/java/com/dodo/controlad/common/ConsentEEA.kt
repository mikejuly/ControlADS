package com.dodo.controlad.common

import android.content.Context
import android.util.Log
import com.dodo.controlad.R
import com.google.ads.consent.*
import java.net.MalformedURLException
import java.net.URL

object ConsentEEA {

    fun checkConsentEEA(context: Context) {
        val consentInformation: ConsentInformation = ConsentInformation.getInstance(context)
        val publisherIds = arrayOf(context.getString(R.string.assent_pub_ads))
        consentInformation.requestConsentInfoUpdate(
            publisherIds,
            object : ConsentInfoUpdateListener {
                override fun onConsentInfoUpdated(consentStatus: ConsentStatus) {
                    Log.e("lalaland ", "nguoi dung $consentStatus")
                }

                override fun onFailedToUpdateConsentInfo(errorDescription: String?) {
                    // User's consent status failed to update.
                }
            })

        ConsentInformation.getInstance(context).addTestDevice("1B5748C2C9CD010065A54ABD7FCA15A2")
        ConsentInformation.getInstance(context).debugGeography = DebugGeography.DEBUG_GEOGRAPHY_EEA
    }
}

fun requestConsentEEA(context: Context, link_policy: String) {
    if (ConsentInformation.getInstance(context).consentStatus == ConsentStatus.PERSONALIZED || ConsentInformation.getInstance(context).consentStatus == ConsentStatus.NON_PERSONALIZED
    ) {
        Log.e("moart tr ", ConsentInformation.getInstance(context).consentStatus.toString())
        //goMain()
    } else {
        var privacyUrl: URL? = null
        try {
            // TODO: Replace with your app's privacy policy URL.
            privacyUrl = URL(link_policy)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        val form =
            ConsentForm.Builder(context, privacyUrl).withListener(object : ConsentFormListener() {
                override fun onConsentFormLoaded() {

                    // form.show()
                }

                override fun onConsentFormOpened() {}
                override fun onConsentFormClosed(
                    consentStatus: ConsentStatus, userPrefersAdFree: Boolean
                ) {
                    if (userPrefersAdFree) {
                        //   purchaseProDialog.show()
                    } else {
                        //    goMain()
                    }
                }

                override fun onConsentFormError(errorDescription: String) {
                    //    goMain();
                }
            })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .withAdFreeOption()
                .build()
        form.load()
    }

}