package com.gounaris.bist.api.controllers.testmethods

data class TestScenarioRequestBody(
    val name: String,
    val steps: List<TestScenarioStep>
)

data class TestScenarioStep(
    val identifier: String,
    val parameters: List<TestScenarioStepParameter>,
    val resultHoldingParameterName: String
)

data class TestScenarioStepParameter(
    val name: String,
    val reference: String?, // if this is not null, the value of this parameter is taken by referencing an existing result
    val value: String? // if this is not null, the value of this parameter is taken by passed value
)