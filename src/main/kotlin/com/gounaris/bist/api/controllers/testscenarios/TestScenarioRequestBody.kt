package com.gounaris.bist.api.controllers.testscenarios

import com.gounaris.bist.internal.testscenario.TestScenario
import com.gounaris.bist.internal.testscenario.TestScenarioStep
import com.gounaris.bist.internal.testscenario.TestScenarioStepParameter

data class TestScenarioRequestBody(
    val name: String,
    val steps: List<TestScenarioStepRequestBody>
)

data class TestScenarioStepRequestBody(
    val identifier: String,
    val parameters: List<TestScenarioStepParameterRequestBody>,
    val resultHoldingParameterName: String
)

data class TestScenarioStepParameterRequestBody(
    val name: String, // this is the identifier for the step function actual param name, so order is not required internally
    val reference: String?, // if this is not null, the value of this parameter is taken by referencing an existing result
    val value: String? // if this is not null, the value of this parameter is taken by passed value
)

fun TestScenarioRequestBody.toDto() =
    TestScenario(null, name, steps.mapIndexed { index, body -> body.toDto(null, index) })

fun TestScenarioStepRequestBody.toDto(scenarioId: Long?, stepOrder: Int) =
    TestScenarioStep(
        null,
        identifier,
        parameters.map { it.toDto(null) },
        resultHoldingParameterName,
        stepOrder)

fun TestScenarioStepParameterRequestBody.toDto(stepId: Long?) =
    TestScenarioStepParameter(null, name, reference, value)