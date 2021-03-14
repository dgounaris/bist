package com.gounaris.bist.api.security

import com.gounaris.bist.api.security.UserDetailsSecurityProviderImpl
import com.gounaris.bist.persistence.user.User
import com.gounaris.bist.persistence.user.UserRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException

internal class UserDetailsSecurityProviderImplTest {
    private val userRepo = mock<UserRepository>().also {
        doReturn(User(1L, "a", "b", "")).whenever(it).findByUsername("a")
    }
    private val provider = UserDetailsSecurityProviderImpl(userRepo)

    @BeforeEach
    fun setup() {
        val auth = mock<Authentication>().also {
            doReturn("a").whenever(it).principal
        }
        val context = mock<SecurityContext>().also {
            doReturn(auth).whenever(it).authentication
        }
        SecurityContextHolder.setContext(context)
    }

    @AfterEach
    fun tearDown() {
        SecurityContextHolder.clearContext()
    }

    @Test
    fun `loadUserByUsername_withExistingUser_succeeds`() {
        val result = provider.loadUserByUsername("a")

        assertEquals("a", result.username)
    }

    @Test
    fun `loadUserByUsername_withNotExistingUser_throws`() {
        assertThatThrownBy { provider.loadUserByUsername("b") }
            .isInstanceOf(UsernameNotFoundException::class.java)
            .hasMessage("Username b matched no user records")
    }

    @Test
    fun `currentUser_withExistingUser_succeeds`() {
        val result = provider.currentUser()

        assertEquals("a", result!!.username)
        assertEquals("b", result.password)
    }

    @Test
    fun `currentUser_withNoExistingUser_returnsNull`() {
        val auth = mock<Authentication>().also {
            doReturn("b").whenever(it).principal
        }
        val context = mock<SecurityContext>().also {
            doReturn(auth).whenever(it).authentication
        }
        SecurityContextHolder.setContext(context)

        val result = provider.currentUser()

        assertNull(result)
    }
}