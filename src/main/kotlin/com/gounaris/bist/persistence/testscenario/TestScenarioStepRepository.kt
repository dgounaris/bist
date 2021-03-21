package com.gounaris.bist.persistence.testscenario

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface TestScenarioStepRepository : JpaRepository<TestScenarioStep, Long> {
    fun findAllByScenarioIdOrderByStepOrderAsc(scenarioId: Long): List<TestScenarioStep>
}

@Component
internal class TestScenarioStepConverter(
    @Autowired private val testScenarioStepParameterRepository: TestScenarioStepParameterRepository,
    @Autowired private val testScenarioStepParameterConverter: TestScenarioStepParameterConverter
) {
    fun toEntity(dto: com.gounaris.bist.internal.testscenario.TestScenarioStep, scenarioId: Long) =
        TestScenarioStep(
            dto.id, scenarioId, dto.identifier, dto.resultHoldingParameterName, dto.stepOrder
        )

    fun toDto(entity: TestScenarioStep) =
        com.gounaris.bist.internal.testscenario.TestScenarioStep(
            entity.id,
            entity.identifier,
            testScenarioStepParameterRepository.findAllByStepId(entity.id!!).map {
                testScenarioStepParameterConverter.toDto(it)
            },
            entity.resultHoldingParameterName,
            entity.stepOrder
        )
}