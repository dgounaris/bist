package com.gounaris.bist.api.exceptions

import com.gounaris.bist.base.objectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionHandlerFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (t: Throwable) {
            val errorResponse = ErrorResponse(t.cause?.message ?: t.message)
            response.status = t.cause?.asStatus() ?: t.asStatus()
            response.contentType = "application/json"
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
            response.writer.flush()
        }
    }

    private fun Throwable.asStatus() = when(this) {
        is IllegalArgumentException -> HttpStatus.BAD_REQUEST.value()
        else -> HttpStatus.INTERNAL_SERVER_ERROR.value()
    }
}