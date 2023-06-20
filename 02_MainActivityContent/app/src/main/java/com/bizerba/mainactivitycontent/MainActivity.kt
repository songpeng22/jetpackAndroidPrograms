package com.bizerba.mainactivitycontent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bizerba.mainactivitycontent.ui.theme.MainActivityContentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme{
                Surface() {
                    MainActivityContent()
                }
            }
        }
    }
}

@Composable
fun Header(image: Int, description: String){
    Image(
        painter = painterResource(id = image),
        contentDescription = description,
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun TemperatureText(celsius: Int){
    val fahrenheit = (celsius.toDouble()*9/5)+32
    Text("$celsius Celsius is $fahrenheit Fahrenheit")
}

@Composable
fun ExampleTextField(){
    val text = remember{mutableStateOf("")}

    TextField(
        value = text.value,
        onValueChange = {text.value = it},
        label = {Text("What is your name?")}
    )
}

@Composable
fun EnterTemperature(temperature: String, changed:(String) -> Unit){
    TextField(
        value = temperature,
        label = {Text("Enter a temperature in Celsius")},
        onValueChange = changed,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ConvertButton(clicked:()-> Unit){
    Button(onClick = clicked){
        Text("Convert")
    }
}

@Composable
fun MainActivityContent() {
    val celsius = remember{
        mutableStateOf(0)
    }
    val newCelsius = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Header(R.drawable.sunrise,"sunrise image")
        EnterTemperature(temperature = newCelsius.value, { newCelsius.value = it } )
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            ConvertButton {
                newCelsius.value.toIntOrNull()?.let {
                    celsius.value = it
                }
            }
        }
        TemperatureText(celsius.value)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme{
        Surface() {
            MainActivityContent()
        }
    }
}