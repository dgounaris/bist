package com.gounaris.bist.internal.testscenario.services

import com.gounaris.bist.internal.testmethods.DynamicTestMethod
import com.gounaris.bist.internal.testmethods.services.dynamicresolve.DynamicMethodResolver
import com.gounaris.bist.internal.testscenario.TestScenario
import com.gounaris.bist.internal.testscenario.TestScenarioPersistence
import com.gounaris.bist.internal.testscenario.TestScenarioStep
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.jupiter.api.Test

internal class TestScenarioExecutorImplTest {
    @Test
    fun `execute_withNonExistingScenario_shouldThrow`() {
        val scenario = mock<TestScenario>()
        val persistence = mock<TestScenarioPersistence>().also {
            doReturn(scenario).whenever(it).findById(1)
        }
        val dynamicMethod = createMockDynamicMethod()
        val resolverCommand = DynamicMethodResolver.ReflectiveMethodResolveCommand("a")
        val resolver = mock<DynamicMethodResolver>().also {
            doReturn(dynamicMethod).whenever(it).resolve(resolverCommand)
        }
        val executor = TestScenarioExecutorImpl(resolver, persistence)

        Assert.assertThrows(
            "No scenario with id 1 found.",
            NoSuchElementException::class.java
        ) { executor.execute(TestScenarioExecutor.TestScenarioExecuteCommand(2)) }
    }

    @Test
    fun `execute_withExistingScenario_withNoSteps_shouldPass`() {
        val scenario = mock<TestScenario>().also {
            doReturn(emptyList<TestScenarioStep>()).whenever(it).steps
        }
        val persistence = mock<TestScenarioPersistence>().also {
            doReturn(scenario).whenever(it).findById(1)
        }
        val dynamicMethod = createMockDynamicMethod()
        val resolver = mock<DynamicMethodResolver>().also {
            doReturn(dynamicMethod).whenever(it).resolve(any())
        }
        val executor = TestScenarioExecutorImpl(resolver, persistence)

        executor.execute(TestScenarioExecutor.TestScenarioExecuteCommand(1))

        verify(resolver, times(0)).resolve(any())
    }

    private fun createMockDynamicMethod() : DynamicTestMethod {
        return mock<DynamicTestMethod>().also {
            doReturn(null).whenever(it).instance
        }
    }
}