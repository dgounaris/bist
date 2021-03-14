package com.gounaris.bist.internal.testscenario

data class TestScenario (
    val id: Long,
    val name: String,
    val stepIds: List<Long>
)