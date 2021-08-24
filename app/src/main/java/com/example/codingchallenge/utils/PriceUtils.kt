package com.example.codingchallenge.utils

import java.math.BigDecimal
import java.math.RoundingMode

object PriceUtils {
    fun doublePriceRoundedToString(price: Double): String =
        BigDecimal(price).setScale(3, RoundingMode.DOWN).toPlainString()
}