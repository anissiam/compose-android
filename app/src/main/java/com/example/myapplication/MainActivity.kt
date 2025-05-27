package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var counter by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color =  Color(0xFF546E7A)
        ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("$ ${counter}" , style = TextStyle(color = Color.White , fontSize = 39.sp , fontWeight = FontWeight.ExtraBold))
            Spacer(modifier= Modifier.height(130.dp))
            CreateCircle(counter = counter ){
                counter = it
            }
        }
    }
}

@Composable
fun CreateCircle(counter :Int = 0  , updatedCounter:(Int)-> Unit) {

    Card(
        modifier = Modifier
            .padding(3.dp)
            .size(105.dp)
            .clickable {
                Log.d("TAP", "CreateCircle: TAP")
                updatedCounter(counter+1)
                Log.d("TAB", "CreateCircle: $counter")

            },
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Text(text = "TAP $counter" , modifier = Modifier)
        }
    }
}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MyApp()
    }
}