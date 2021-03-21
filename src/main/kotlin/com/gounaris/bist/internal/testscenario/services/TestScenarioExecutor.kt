package com.gounaris.bist.internal.testscenario.services

import com.gounaris.bist.internal.testmethods.DynamicMethodParameter
import com.gounaris.bist.internal.testmethods.services.dynamicresolve.DynamicMethodResolver
import com.gounaris.bist.internal.testscenario.TestScenarioPersistence
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface TestScenarioExecutor {
    fun execute(command: TestScenarioExecuteCommand) // todo add list of step result as return

    data class TestScenarioExecuteCommand(val id: Long)
}

@Service
class TestScenarioExecutorImpl(
    @Autowired private val dynamicMethodResolver: DynamicMethodResolver,
    @Autowired private val testScenarioPersistence: TestScenarioPersistence
): TestScenarioExecutor {
    override fun execute(command: TestScenarioExecutor.TestScenarioExecuteCommand) {
        val scenario = testScenarioPersistence.findById(command.id) ?: throw NoSuchElementException(
            "No scenario with id ${command.id} found."
        )
        val methodsToExecute = scenario.steps
            .map { step ->
                dynamicMethodResolver.resolve(
                    DynamicMethodResolver.ReflectiveMethodResolveCommand(step.identifier)
                ) ?: throw NoSuchElementException("No scenario step with name ${step.identifier} found.")
            }
        methodsToExecute.forEach {
            // todo add translation and order of parameters based on the dynamic info
            val result = it.method.invoke(it.instance)
            println("Result from successful execution: $result")
        }
    }
}