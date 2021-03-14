package com.gounaris.bist.internal.user.services

import com.gounaris.bist.persistence.user.User
import com.gounaris.bist.persistence.user.UserRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserRegistrationServiceImplTest {
    private val testEntityA = User(1L, "a", "b", "")
    private val testEntityB = User(1L, "b", "b", "")
    private val userRepo = mock<UserRepository>().also {
        doReturn(testEntityA).whenever(it).findByUsername("a")
        doReturn(null).whenever(it).findByUsername("b")
        doReturn(testEntityB).whenever(it).save(any())
    }
    private val service = UserRegistrationServiceImpl(userRepo)

    @Test
    fun `register_withNewUsername_succeeds`() {
        val result = service.register(UserRegistrationService.UserRegisterCommand("b", "b", ""))

        assertEquals("b", result.username)
        assertEquals("b", result.password)
    }

    @Test
    fun `register_withExistingUsername_throws`() {
        assertThatThrownBy {
            service.register(UserRegistrationService.UserRegisterCommand("a", "b", ""))
        }.isInstanceOf(IllegalArgumentException::class.java).hasMessage("User with username a already exists")
    }
}