package com.jax.roulettegame.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest

@Composable
fun YandexBanner(id: Int) {
    AndroidView(factory = { context ->
        BannerAdView(context).apply {
            setAdUnitId(context.getString(id))
            setAdSize(AdSize.stickySize(context, 300))
            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)
        }
    })
}