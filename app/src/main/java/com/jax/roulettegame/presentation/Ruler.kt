package com.jax.roulettegame.presentation

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.roulettegame.R
import com.jax.roulettegame.utils.ValueList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun RunGame(
    context: Context,
    adController: AdController
) {
    var gameJob: Job
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()){
            Torque()
            Spacer(modifier = Modifier.height(10.dp))
            AdMobBanner(id = R.string.banner_ad_mob)
            Spacer(modifier = Modifier.height(10.dp))
            YandexBanner(id = R.string.banner_yandex)
        }

        DisposableEffect(Unit) {
            gameJob = CoroutineScope(Dispatchers.Main).launch {
                while (isActive) {
                    runAdvert(context, adController)
                    delay(FIVE_MINUTES)
                }
            }
            onDispose {
                gameJob.cancel()
            }
        }
    }
}
const val FIVE_MINUTES = 5*60*1000L
private fun runAdvert(
    context: Context,
    adController: AdController
){
    adController.rewardedAd?.let { rewardedAd ->
        rewardedAd.show(context as Activity) { _ ->
            Toast.makeText(context, "Adverts for using the app", Toast.LENGTH_SHORT).show()
        }
    }
}


@Composable
private fun Torque(){

    var rotationValue by remember {
        mutableFloatStateOf(0f)
    }
    var number by remember {
        mutableIntStateOf(0)
    }
    var color by remember {
        mutableIntStateOf(3)
    }
    val angle by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = 3400,
            easing = FastOutSlowInEasing
        ),
        finishedListener = {
            val index = (360f - (it % 360)) / (360f / 37f)
            number = ValueList.list[index.toInt()]
            color = if((index%2).roundToInt() == 0) 0 else 1
        }, label = ""
    )
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.80f)
        .background(Color.White))
    {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(top = 8.dp),
            text = number.toString(),
            style = MaterialTheme.typography.displayMedium.copy(fontSize = 35.sp),
            color = if(color==3) Color.Green else if(color==0) Color.Red else Color.Black
        )
       Box(modifier = Modifier
           .fillMaxSize()
           .background(Color.White)
           .weight(1f)
       ){
           Image(
               modifier = Modifier
                   .fillMaxSize()
                   .rotate(degrees = angle + rotationValue),
               painter = painterResource(id = R.drawable.roulette),
               contentDescription = null
           )
           Image(
               modifier = Modifier
                   .fillMaxSize()
                   .align(Alignment.Center),
               painter = painterResource(id = R.drawable.arrowbottom),
               contentDescription = null
           )
       }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(195, 205, 222),
                contentColor = Color(54, 52, 51)
            ),
            onClick = {
                rotationValue =  (720..1080).random().toFloat() + angle
            }
        ){
            Text(
                text = "spin",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp)
            )
        }
    }
}