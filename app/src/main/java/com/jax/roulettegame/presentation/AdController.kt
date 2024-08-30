package com.jax.roulettegame.presentation

import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class AdController(
    private val context: Context,
    var rewardedAd: RewardedAd? = null
) {

    init { loadAd() }

    private fun loadAd(){
        val request = AdRequest.Builder()
            .addKeyword("gaming")
            .setContentUrl("https://example.com")
            .build()
        RewardedAd.load(context, AD_ID,request, object : RewardedAdLoadCallback(){
            override fun onAdLoaded(ad: RewardedAd) {
                super.onAdLoaded(ad)
                rewardedAd = ad
                rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback(){
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        rewardedAd = null
                        loadAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(ad: AdError) {
                        super.onAdFailedToShowFullScreenContent(ad)
                        rewardedAd = null
                        loadAd()
                    }
                }
            }
        })
    }
    companion object{
        private const val AD_ID = "ca-app-pub-3940256099942544/5224354917"
    }
}