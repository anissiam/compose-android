package com.example.myapplication.jetTipApp.util

fun calculateTotalTip(totalBill: Double, tipPerc: Int): Double {
    return if (totalBill > 1 && totalBill.toString().isNotEmpty())
        (totalBill * tipPerc) / 100 else 0.0
}

fun calculateTotalPerPerson(
    totalBill: Double,
    splitBy: Int,
    tipPerc: Int): Double {
    val bill = calculateTotalTip(totalBill = totalBill, tipPerc = tipPerc) + totalBill

    return (bill/splitBy)
}
