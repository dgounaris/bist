package com.gounaris.bist.persistence.testscenario

import com.gounaris.bist.internal.testscenario.TestScenarioPersistence
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Component
internal class TestScenarioPersistenceImpl(
    @Autowired private val testScenarioRepository: TestScenarioRepository,
    @Autowired private val testScenarioStepRepository: TestScenarioStepRepository,
    @Autowired private val testScenarioStepParameterRepository: TestScenarioStepParameterRepository,
    @Autowired private val testScenarioConverter: TestScenarioConverter,
    @Autowired private val testScenarioStepConverter: TestScenarioStepConverter,
    @Autowired private val testScenarioStepParameterConverter: TestScenarioStepParameterConverter
) : TestScenarioPersistence {
    @Transactional
    override fun save(testScenario: com.gounaris.bist.internal.testscenario.TestScenario):
            com.gounaris.bist.internal.testscenario.TestScenario
    {
        val savedScenario = testScenarioRepository.save(
            testScenarioConverter.toEntity(testScenario)
        )
        val stepsToSavedSteps = testScenario.steps.map {
            it to testScenarioStepRepository.save(testScenarioStepConverter.toEntity(it, savedScenario.id!!))
        }

        stepsToSavedSteps.flatMap { stepPair ->
            stepPair.first.parameters.map { param ->
                testScenarioStepParameterConverter.toEntity(param, stepPair.second.id!!)
            }
        }.also { testScenarioStepParameterRepository.saveAll(it) }

        return testScenarioConverter.toDto(savedScenario)
    }

    override fun findById(id: Long): com.gounaris.bist.internal.testscenario.TestScenario? =
        testScenarioRepository.findById(id).orElse(null)?.let {
            testScenarioConverter.toDto(it)
        }

}

@Repository
interface TestScenarioRepository : JpaRepository<TestScenario, Long> {
    fun findByName(name: String): TestScenario?
}


@Component
internal class TestScenarioConverter(
    @Autowired private val testScenarioStepRepository: TestScenarioStepRepository,
    @Autowired private val testScenarioStepConverter: TestScenarioStepConverter
) {
    fun toEntity(dto: com.gounaris.bist.internal.testscenario.TestScenario) =
        TestScenario(dto.id, dto.name)

    fun toDto(entity: TestScenario) =
        com.gounaris.bist.internal.testscenario.TestScenario(
            entity.id, entity.name, testScenarioStepRepository.findAllByScenarioId(entity.id!!).map {
                testScenarioStepConverter.toDto(it)
            }
        )
}