package com.gounaris.bist.internal.testscenario

data class TestScenarioStep (
    val id: Long,
    val scenarioId: Long,
    val identifier: String,
    val parameterIds: List<Long>,
    val resultHoldingParameterName: String
)