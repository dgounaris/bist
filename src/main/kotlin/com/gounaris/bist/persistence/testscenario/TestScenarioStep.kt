package com.gounaris.bist.persistence.testscenario

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "test_scenario_steps")
data class TestScenarioStep (
    @Id
    @SequenceGenerator(name = "test_scenario_step_seq", sequenceName = "test_scenario_step_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_scenario_step_seq")
    val id: Long? = null,

    @Column(nullable = false)
    val scenarioId: Long = 0,

    @Column(nullable = false)
    val identifier: String = "",

    @Column(nullable = true)
    val resultHoldingParameterName: String? = null,

    @Column(nullable = false)
    val stepOrder: Int = 0
)