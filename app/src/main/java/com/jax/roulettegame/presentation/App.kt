package com.jax.roulettegame.presentation

import android.app.Application
import com.google.android.gms.ads.MobileAds

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        com.yandex.mobile.ads.common.MobileAds.initialize(this){ }
        MobileAds.initialize(this) {}
    }
}