package com.gounaris.bist.internal.testscenario

data class TestScenario (
    val id: Long?,
    val name: String,
    val steps: List<TestScenarioStep>
)

interface TestScenarioPersistence {
    fun save(testScenario: TestScenario): TestScenario
    fun findById(id: Long): TestScenario?
}