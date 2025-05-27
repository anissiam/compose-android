package com.example.myapplication.jetTipApp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.jetTipApp.componants.InputField
import com.example.myapplication.jetTipApp.util.calculateTotalPerPerson
import com.example.myapplication.jetTipApp.util.calculateTotalTip
import com.example.myapplication.jetTipApp.widget.RoundedIconButton
import com.example.myapplication.ui.theme.MyApplicationTheme

// this code from https://github.com/pdichone/jetTip-Test/tree/jetTip-with-hoisting
class HetTapAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MyApplicationTheme {
        Scaffold {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(it)
            ) {
                content()
            }
        }

    }
}


@Preview
@Composable
fun TopHeader(modifier: Modifier = Modifier, totalPerPerson: Double = 0.0) {
    /*Card (modifier= Modifier.fillMaxWidth().height(150.dp),
        shape = RoundedCornerShape(12.dp)
    ){

    }*/
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(vertical = 20.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
        //.clip(shape = RoundedCornerShape(corner = CornerSize(12.dp)))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                "Total per person",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                "$$total",
                style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview
@Composable
fun MainContent(modifier: Modifier = Modifier) {


    BillForm() { bill ->
        Log.d("TAG0101", "MainContent: $bill")
    }


}

@Composable
fun BillForm(modifier: Modifier = Modifier, onValueChange: (String) -> Unit = {}) {
    val totalBillState = remember { mutableStateOf("") }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val totalPerPerson = remember {
        mutableStateOf(0.0)
    }
    val keybordController = LocalSoftwareKeyboardController.current
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    val tipPercentage = (sliderPositionState.value * 100).toInt()
    val splitByState = remember {
        mutableStateOf(1)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }
    val range = IntRange(start = 1, endInclusive = 100)

    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    Column(modifier = Modifier.padding(all = 12.dp)) {
        TopHeader(totalPerPerson = totalPerPersonState.value)
        Surface(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(6.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                InputField(
                    valueState = totalBillState,
                    labelId = "Enter Bill",
                    enabled = true,
                    isSingleLine = true,
                    onAction = KeyboardActions {
                        if (!validState) return@KeyboardActions

                        onValueChange(totalBillState.value.trim())
                        keybordController?.hide()
                    }
                )
                if (validState) {
                    Row(
                        modifier = Modifier.padding(3.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            "Split", modifier = Modifier.align(
                                alignment = Alignment.CenterVertically
                            )
                        )
                        Spacer(modifier = Modifier.width(120.dp))
                        Row(
                            modifier = Modifier.padding(horizontal = 3.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            RoundedIconButton(
                                imageVector = Icons.Default.Remove,
                                onClick = {
                                    splitByState.value =
                                        if (splitByState.value > 1) splitByState.value - 1
                                        else 1
                                    totalPerPersonState.value =
                                        calculateTotalPerPerson(
                                            totalBill = totalBillState.value.toDouble(),
                                            splitBy = splitByState.value,
                                            tipPerc = tipPercentage
                                        )
                                })
                            Text(
                                splitByState.value.toString(),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 9.dp, end = 9.dp)
                            )
                            RoundedIconButton(
                                imageVector = Icons.Default.Add,
                                onClick = {
                                    if (splitByState.value < range.last) {
                                        splitByState.value = splitByState.value + 1
                                        totalPerPersonState.value =
                                            calculateTotalPerPerson(
                                                totalBill = totalBillState.value.toDouble(),
                                                splitBy = splitByState.value,
                                                tipPerc = tipPercentage
                                            )
                                    }
                                })
                        }
                    }
                    Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                        Text(
                            "Tip",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(200.dp))
                        Text(
                            "${tipAmountState.value}",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Column(modifier = Modifier.padding(horizontal = 3.dp)) {
                        Text(
                            "$tipPercentage%",
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Slider(
                            steps = 5,
                            value = sliderPositionState.value,
                            onValueChange = {
                                sliderPositionState.value = it
                                tipAmountState.value = calculateTotalTip(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPerc = tipPercentage
                                )


                                totalPerPersonState.value =
                                    calculateTotalPerPerson(
                                        totalBill = totalBillState.value.toDouble(),
                                        splitBy = splitByState.value,
                                        tipPerc = tipPercentage
                                    )
                                Log.d("TAG0101", "BillForm: ${tipAmountState.value}")
                            },
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            onValueChangeFinished = {

                            })
                    }


                } else {

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyApp {

    }
}