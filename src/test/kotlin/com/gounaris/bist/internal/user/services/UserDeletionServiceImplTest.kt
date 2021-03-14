package com.gounaris.bist.internal.user.services

import com.gounaris.bist.internal.user.services.UserDeletionService
import com.gounaris.bist.internal.user.services.UserDeletionServiceImpl
import com.gounaris.bist.persistence.user.User
import com.gounaris.bist.persistence.user.UserRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserDeletionServiceImplTest {
    private val testEntityA = User(1L, "a", "b", "")
    private val userRepo = mock<UserRepository>().also {
        doReturn(testEntityA).whenever(it).findByUsername("a")
        doReturn(null).whenever(it).findByUsername("b")
    }
    private val service = UserDeletionServiceImpl(userRepo)

    @Test
    fun `delete_withExistingUsername_succeeds`() {
        val result = service.delete(UserDeletionService.UserDeleteCommand("a"))

        assertEquals("a", result.username)
        assertEquals("b", result.password)
    }

    @Test
    fun `delete_withNonExistingUsername_throws`() {
        assertThatThrownBy {
            service.delete(UserDeletionService.UserDeleteCommand("b"))
        }.isInstanceOf(NoSuchElementException::class.java).hasMessage("No user with username b found.")
    }
}