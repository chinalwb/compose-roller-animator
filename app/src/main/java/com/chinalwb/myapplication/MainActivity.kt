package com.chinalwb.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .verticalScroll(scrollState)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = "Roller Animator Demo App", style = MaterialTheme.typography.h5)

        Text(text = """
            Settings: 
            animateOneByOne = false,
            duration = 600,
            ltr = false,
            animateToSameNumber = false
        """.trimIndent())
        RollerAnimatorDemo(
            animateOneByOne = false,
            duration = 600,
            ltr = false,
            animateToSameNumber = false,
        )

        Spacer(modifier = Modifier.height(64.dp))
        Divider()

        Text(text = """
            Settings: 
            animateOneByOne = true,
            duration = 600,
            ltr = false,
            animateToSameNumber = false
        """.trimIndent())

        RollerAnimatorDemo(
            animateOneByOne = true,
            duration = 600,
            ltr = false,
            animateToSameNumber = false,
        )

        Spacer(modifier = Modifier.height(64.dp))
        Divider()

        Text(text = """
            Settings: 
            animateOneByOne = true,
            duration = 600,
            ltr = true,
            animateToSameNumber = true
        """.trimIndent())

        RollerAnimatorDemo(
            animateOneByOne = true,
            duration = 600,
            ltr = true,
            animateToSameNumber = true,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}