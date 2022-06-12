package com.chinalwb.myapplication

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chinalwb.compose.rolleranimator.RollerNumberAnimator
import kotlin.random.Random

@Composable
fun RollerAnimatorDemo(
    animateOneByOne: Boolean = true,
    ltr: Boolean = false,
    duration: Int = 1000,
    animateToSameNumber: Boolean = false
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var startValue by remember {
            mutableStateOf(1990)
        }
        var endValue by remember {
            mutableStateOf(1990)
        }
        var decrease by remember {
            mutableStateOf(false)
        }

        RollerNumberAnimator(
            startValue = startValue,
            endValue = endValue,
            animateOneByOne = animateOneByOne,
            ltr = ltr,
            decrease = decrease,
            duration = duration,
            animateToSameNumber = animateToSameNumber
        ) {
            Log.w("xx", "roller finished = $it")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                startValue = endValue
                endValue += 1
                decrease = false
            }) {
                Text(text = "+1")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                startValue = endValue
                endValue -= 1
                decrease = true
            }) {
                Text(text = "-1")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                startValue = endValue
                endValue += 10
                decrease = false
            }) {
                Text(text = "+10")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                startValue = endValue
                endValue -= 10
                decrease = true
            }) {
                Text(text = "-10")
            }
        }


        Spacer(modifier = Modifier.width(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                startValue = endValue
                endValue += Random.nextInt(1000)
                decrease = false
            }) {
                Text(text = "+random")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                startValue = endValue
                endValue -= Random.nextInt(1000)
                decrease = true
            }) {
                Text(text = "-random")
            }
        }
    }

}