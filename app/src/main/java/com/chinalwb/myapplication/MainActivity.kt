package com.chinalwb.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chinalwb.compose.rolleranimator.RollerNumberAnimator
import com.chinalwb.myapplication.ui.theme.MyApplicationTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
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
        RollerNumberAnimator(startValue = startValue, endValue = endValue, animateOneByOne = true, ltr = false, decrease = decrease, duration = 1000, animateToSameNumber = false) {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}