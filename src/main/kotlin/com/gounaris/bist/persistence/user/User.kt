package com.gounaris.bist.persistence.user

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val username: String? = null,

    @Column(nullable = false)
    val password: String? = null,

    @Column
    val email: String? = null
)