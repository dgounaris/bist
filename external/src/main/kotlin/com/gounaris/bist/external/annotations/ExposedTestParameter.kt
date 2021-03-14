package com.gounaris.bist.external.annotations

// this annotation is necessary because java compilation is not guaranteed to expose the param name
annotation class ExposedTestParameter(
    val name: String
)

fun ExposedTestParameter.name() = this.name