package com.gounaris.bist.api.controllers

import com.gounaris.bist.api.controllers.user.UserController
import com.gounaris.bist.api.controllers.user.models.UserRegisterRequestBody
import com.gounaris.bist.internal.user.UserDto
import com.gounaris.bist.internal.user.services.UserRegistrationService
import com.gounaris.bist.api.security.UserDetailsSecurityProvider
import com.gounaris.bist.internal.user.services.UserDeletionService
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test

internal class UserControllerTest {
    private val testUserDto = UserDto(1L, "", "", "")
    private val registrationService = mock<UserRegistrationService>().also {
        doReturn(testUserDto).whenever(it).register(any())
    }
    private val deletionService = mock<UserDeletionService>().also {
        doReturn(testUserDto).whenever(it).delete(any())
    }
    private val securityProvider = mock<UserDetailsSecurityProvider>().also {
        doReturn(testUserDto).whenever(it).currentUser()
    }
    private val controller = UserController(registrationService, deletionService, securityProvider)

    @Test
    fun `register_delegatesTo_RegistrationService`() {
        val body = UserRegisterRequestBody("", "", "")

        controller.register(body)

        verify(registrationService, times(1)).register(any())
    }

    @Test
    fun `currentUser_delegatesTo_securityProvider`() {
        controller.whoami()

        verify(securityProvider, times(1)).currentUser()
    }

    @Test
    fun `deleteUser_delegatesTo_securityProvider`() {
        controller.delete("a")

        verify(deletionService, times(1)).delete(UserDeletionService.UserDeleteCommand("a"))
    }
}