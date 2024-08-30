package com.jax.roulettegame.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.ads.MobileAds
import com.jax.roulettegame.ui.theme.RouletteGameTheme


class MainActivity : ComponentActivity() {

    private lateinit var adController: AdController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)
        adController = AdController(context = this)
        setContent {
            RouletteGameTheme {
                RunGame(context = this , adController = adController )
            }
        }
    }
}

