package com.gounaris.bist.dogfood

import com.gounaris.bist.external.annotations.ExposedTestGiven
import com.gounaris.bist.external.annotations.ExposedTestParameter

class TestGivenFunctionsExample {
    companion object {
        @JvmStatic
        @ExposedTestGiven("Given a 0")
        fun number0(): Int = 0

        @JvmStatic
        @ExposedTestGiven("Given a 1")
        fun number1(): Long = 1L

        @JvmStatic
        @ExposedTestGiven("Given a \${number}")
        fun numberAny(@ExposedTestParameter("number") number: String): Int = number.toInt()
    }
}