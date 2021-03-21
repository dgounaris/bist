package com.gounaris.bist.persistence.testscenario

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "test_scenario_step_params")
data class TestScenarioStepParameter (
    @Id
    @SequenceGenerator(name = "test_scenario_step_param_seq", sequenceName = "test_scenario_step_param_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_scenario_step_param_seq")
    val id: Long? = null,

    @Column(nullable = false)
    val stepId: Long = 0,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = true)
    val reference: String? = null,

    @Column(nullable = true)
    val paramValue: String? = null
    // todo add step parameter order to recreate list order
)