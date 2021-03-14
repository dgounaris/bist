package com.gounaris.bist.controllers

import com.gounaris.bist.IntegrationTestBase
import com.gounaris.bist.client.RemoteUnirestClient
import com.gounaris.bist.client.WebRequest
import com.gounaris.bist.client.HttpVerb
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HealthCheckControllerIntegrationTest : IntegrationTestBase() {
    @Test
    fun `healthCheck_succeeds`() {
        val request = WebRequest<Any>("/health")

        val result = RemoteUnirestClient(serverHostAndPort).call(HttpVerb.GET, request)

        assertEquals("OK", result.body)
    }
}