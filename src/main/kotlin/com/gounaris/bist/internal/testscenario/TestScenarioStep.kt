package com.gounaris.bist.internal.testscenario

data class TestScenarioStep (
    val id: Long?,
    val identifier: String,
    val parameters: List<TestScenarioStepParameter>,
    val resultHoldingParameterName: String?,
    val stepOrder: Int
)