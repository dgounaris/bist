package com.gounaris.bist.controllers

import com.gounaris.bist.IntegrationTestBase
import com.gounaris.bist.api.controllers.user.models.UserLoginRequestBody
import com.gounaris.bist.api.controllers.user.models.UserRegisterRequestBody
import com.gounaris.bist.api.exceptions.ErrorResponse
import com.gounaris.bist.client.HttpVerb
import com.gounaris.bist.client.RemoteUnirestClient
import com.gounaris.bist.client.WebRequest
import com.gounaris.bist.internal.user.UserDto
import com.gounaris.bist.persistence.user.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserControllerIntegrationTest : IntegrationTestBase() {
    @Test
    fun `register_succeeds`() {
        val body = UserRegisterRequestBody("a", "b", "c")
        val request = WebRequest("/user/register", body)

        val result = RemoteUnirestClient(serverHostAndPort).call(HttpVerb.POST, request, UserDto::class.java)

        assertEquals("a", result.body!!.username)
        assert(dbAccessor.singleFromTable("users"))
        assertEquals(
            1,
            dbAccessor.fetchAllMatchingFromTable("users", User::class.java) { it.username == "a" }.size
        )
    }

    @Test
    fun `duplicate_register_fails`() {
        val body = UserRegisterRequestBody("c", "b", "c")
        val request = WebRequest("/user/register", body)
        RemoteUnirestClient(serverHostAndPort).call(HttpVerb.POST, request, UserDto::class.java)

        val duplicateCallResult = RemoteUnirestClient(serverHostAndPort).call(HttpVerb.POST, request, ErrorResponse::class.java)

        assertEquals("User with username c already exists", duplicateCallResult.body!!.message)
        assert(dbAccessor.singleFromTable("users"))
        assertEquals(
            1,
            dbAccessor.fetchAllMatchingFromTable("users", User::class.java) { it.username == "c" }.size
        )
    }

    @Test
    fun `login_succeeds`() {
        val body = UserRegisterRequestBody("b", "b", "c")
        val request = WebRequest("/user/register", body)
        RemoteUnirestClient(serverHostAndPort).call(HttpVerb.POST, request, UserDto::class.java)

        val loginBody = UserLoginRequestBody("b", "b")
        val loginRequest = WebRequest("/user/login", loginBody)

        val loginResult = RemoteUnirestClient(serverHostAndPort).call(HttpVerb.POST, loginRequest)

        assert(loginResult.body!!.startsWith("b "))
    }
}