package com.gounaris.bist.external.annotations

annotation class ExposedTestGiven(
    val description: String
)

fun ExposedTestGiven.description() = this.description // allows us to universally apply defaults etc later