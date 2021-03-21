package com.gounaris.bist.api.controllers.testscenarios

import com.gounaris.bist.internal.testscenario.services.TestScenarioCreator
import com.gounaris.bist.internal.testscenario.services.TestScenarioRetriever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/testscenarios")
class TestScenarioController(
    @Autowired private val testScenarioCreator: TestScenarioCreator,
    @Autowired private val testScenarioRetriever: TestScenarioRetriever
) {
    @PutMapping("/save")
    fun saveScenario(@RequestBody scenario: TestScenarioRequestBody) =
        testScenarioCreator.create(
            TestScenarioCreator.TestScenarioCreateCommand(scenario.toDto())
        )

    @GetMapping("/{id}")
    fun getScenario(@PathVariable("id") id: Long) =
        testScenarioRetriever.retrieve(
            TestScenarioRetriever.TestScenarioRetrieveCommand(id)
        )


}