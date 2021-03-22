package com.gounaris.bist.internal.testscenario.services

import com.gounaris.bist.internal.testscenario.TestScenario
import com.gounaris.bist.internal.testscenario.TestScenarioPersistence
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.jupiter.api.Test

internal class TestScenarioCreatorImplTest {
    @Test
    fun `create_succeeds`() {
        val input = mock<TestScenario>()
        val command = TestScenarioCreator.TestScenarioCreateCommand(input)
        val persistence = mock<TestScenarioPersistence>().also {
            doReturn(input).whenever(it).save(input)
        }
        val creator = TestScenarioCreatorImpl(persistence)

        val result = creator.create(command)

        Assert.assertEquals(input, result)
    }
}