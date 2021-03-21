package com.gounaris.bist.persistence.testscenario

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface TestScenarioStepParameterRepository : JpaRepository<TestScenarioStepParameter, Long> {
    fun findAllByStepId(scenarioId: Long): List<TestScenarioStepParameter>
}

@Component
class TestScenarioStepParameterConverter(
    @Autowired private val testScenarioStepParameterRepository: TestScenarioStepParameterRepository
) {
    fun toEntity(dto: com.gounaris.bist.internal.testscenario.TestScenarioStepParameter, stepId: Long) =
        com.gounaris.bist.persistence.testscenario.TestScenarioStepParameter(
            dto.id, stepId, dto.name, dto.reference, dto.value
        )

    fun toDto(entity: com.gounaris.bist.persistence.testscenario.TestScenarioStepParameter) =
        com.gounaris.bist.internal.testscenario.TestScenarioStepParameter(
            entity.id,
            entity.name,
            entity.reference,
            entity.paramValue
        )
}