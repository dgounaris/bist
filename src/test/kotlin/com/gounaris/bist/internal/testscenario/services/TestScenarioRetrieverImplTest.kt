package com.gounaris.bist.internal.testscenario.services

import com.gounaris.bist.internal.testscenario.TestScenario
import com.gounaris.bist.internal.testscenario.TestScenarioPersistence
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.jupiter.api.Test

internal class TestScenarioRetrieverImplTest {
    @Test
    fun `retrieve_withExistingId_returns`() {
        val scenario = mock<TestScenario>()
        val command = TestScenarioRetriever.TestScenarioRetrieveCommand(1)
        val persistence = mock<TestScenarioPersistence>().also {
            doReturn(scenario).whenever(it).findById(1)
        }
        val retriever = TestScenarioRetrieverImpl(persistence)

        val result = retriever.retrieve(command)

        Assert.assertEquals(scenario, result)
    }

    @Test
    fun `retrieve_withNonExistingId_returnsNull`() {
        val scenario = mock<TestScenario>()
        val command = TestScenarioRetriever.TestScenarioRetrieveCommand(2)
        val persistence = mock<TestScenarioPersistence>().also {
            doReturn(scenario).whenever(it).findById(1)
        }
        val retriever = TestScenarioRetrieverImpl(persistence)

        val result = retriever.retrieve(command)

        Assert.assertNull(result)
    }
}