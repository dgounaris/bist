package com.gounaris.bist.internal.testscenario.services

import com.gounaris.bist.internal.testscenario.TestScenario
import com.gounaris.bist.internal.testscenario.TestScenarioPersistence
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface TestScenarioCreator {
    fun create(command: TestScenarioCreateCommand): TestScenario?

    data class TestScenarioCreateCommand(val scenario: TestScenario)
}

@Service
class TestScenarioCreatorImpl(
    @Autowired private val testScenarioPersistence: TestScenarioPersistence
): TestScenarioCreator {
    override fun create(command: TestScenarioCreator.TestScenarioCreateCommand): TestScenario =
        testScenarioPersistence.save(command.scenario)
}