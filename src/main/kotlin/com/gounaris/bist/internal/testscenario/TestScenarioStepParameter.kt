package com.gounaris.bist.internal.testscenario

data class TestScenarioStepParameter(
    val id: Long,
    val stepId: Long,
    val name: String,
    val reference: String?,
    val value: String?
)