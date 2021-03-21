package com.gounaris.bist.internal.testscenario.services

import com.gounaris.bist.internal.testscenario.TestScenario
import com.gounaris.bist.internal.testscenario.TestScenarioPersistence
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface TestScenarioRetriever {
    fun retrieve(command: TestScenarioRetrieveCommand): TestScenario?

    data class TestScenarioRetrieveCommand(val id: Long)
}

@Service
class TestScenarioRetrieverImpl(
    @Autowired private val testScenarioPersistence: TestScenarioPersistence
) : TestScenarioRetriever {
    override fun retrieve(command: TestScenarioRetriever.TestScenarioRetrieveCommand): TestScenario? =
        testScenarioPersistence.findById(command.id)
}