package com.gounaris.bist.api.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gounaris.bist.api.controllers.user.models.UserLoginRequestBody
import com.gounaris.bist.base.objectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(private val authManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {
    init {
        setFilterProcessesUrl("/user/login")
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val creds = objectMapper.readValue(request.inputStream, UserLoginRequestBody::class.java)
            return authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    creds.username,
                    creds.password,
                    emptyList()
                )
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        with(authResult.principal as UserDetails) {
            val token = JWT.create()
                .withSubject(this.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 900_000))
                .sign(Algorithm.HMAC512("SECRET"))

            val body = "${this.username} $token"
            response.writer.write(body)
            response.writer.flush()
        }
    }
}