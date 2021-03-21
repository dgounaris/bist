package com.gounaris.bist.persistence.testscenario

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "test_scenarios")
data class TestScenario (
    @Id
    @SequenceGenerator(name = "test_scenario_seq", sequenceName = "test_scenario_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_scenario_seq")
    val id: Long? = null,

    @Column(nullable = false)
    val name: String = ""
)