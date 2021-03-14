package com.gounaris.bist.external.tools

object ExposedTestMethodConstants {
    val parameterRegex = "\\\$\\{(.*?)}".toRegex() // ${...}
}