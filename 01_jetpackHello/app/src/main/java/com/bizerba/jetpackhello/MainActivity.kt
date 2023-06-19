package com.bizerba.jetpackhello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bizerba.jetpackhello.ui.theme.JetpackHelloTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column() {
                Text("Hello Jetpack")
                Spacer(modifier = Modifier.height(10.dp))
                Greeting("Greeting fun")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text (text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun PreviewMainActivity(){
    Column() {
        Text("Hello Jetpack2")
        Spacer(modifier = Modifier.height(10.dp))
        Greeting("Greeting fun")
    }
}