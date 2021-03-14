package com.gounaris.bist.api.controllers.testmethods

import com.gounaris.bist.internal.testmethods.services.retrieve.TestMethodsRetrievalByTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/testmethods")
class TestMethodsController(
    @Autowired private val testMethodsRetrievalService: TestMethodsRetrievalByTypeService
) {
    @GetMapping("/given")
    fun givenMethods() = testMethodsRetrievalService.retrieve(
        TestMethodsRetrievalByTypeService.TestMethodsRetrievalCommand(
            TestMethodsRetrievalByTypeService.TestMethodType.GIVEN
        )
    )

    @PutMapping("/scenario/save")
    fun saveScenario(@RequestBody scenario: TestScenarioRequestBody) {
        //todo save scenario. then try to execute, lookup on retrieval service for the method and the details,
        //todo proper cast for variables, keep result
    }
}