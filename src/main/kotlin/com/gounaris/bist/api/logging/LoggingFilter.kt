package com.gounaris.bist.api.logging

import org.springframework.web.util.ContentCachingResponseWrapper

import org.springframework.web.util.ContentCachingRequestWrapper

import javax.servlet.ServletException

import javax.servlet.http.HttpServletResponse

import javax.servlet.http.HttpServletRequest

import java.io.UnsupportedEncodingException

import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain


class LoggingFilter : OncePerRequestFilter() {
    private val includeResponsePayload = true
    private val maxPayloadLength = 1000

    /**
     * Log each request and respponse with full Request URI, content payload and duration of the request in ms.
     * @param request the request
     * @param response the response
     * @param filterChain chain of filters
     * @throws ServletException
     * @throws IOException
     */
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val startTime = System.currentTimeMillis()
        val reqInfo = StringBuffer()
            .append("[")
            .append(startTime % 10000) // request ID
            .append("] ")
            .append(request.method)
            .append(" ")
            .append(request.requestURL)
        val queryString = request.queryString
        if (queryString != null) {
            reqInfo.append("?").append(queryString)
        }
        if (request.authType != null) {
            reqInfo.append(", authType=")
                .append(request.authType)
        }
        if (request.userPrincipal != null) {
            reqInfo.append(", principalName=")
                .append(request.userPrincipal.name)
        }
        logger.info("=> $reqInfo")

        // ========= Log request and response payload ("body") ========
        // We CANNOT simply read the request payload here, because then the InputStream would be consumed
        // and cannot be read again by the actual processing/server.
        // So we need to apply some stronger magic here :-)
        val wrappedRequest = ContentCachingRequestWrapper(request)
        val wrappedResponse = ContentCachingResponseWrapper(response)
        filterChain.doFilter(wrappedRequest, wrappedResponse) // ======== This performs the actual request!
        val duration = System.currentTimeMillis() - startTime

        // I can only log the request's body AFTER the request has been made and ContentCachingRequestWrapper did its work.
        val requestBody = wrappedRequest.contentAsByteArray.decodeToString()
        if (requestBody.length > 0) {
            logger.info("Request body: $requestBody")
        }
        logger.info("<= " + reqInfo + ": returned status=" + response.status + " in " + duration + "ms")
        if (includeResponsePayload) {
            val buf = wrappedResponse.contentAsByteArray
            logger.info("Response body: ${buf.decodeToString()}")
        }
        wrappedResponse.copyBodyToResponse() // IMPORTANT: copy content of response back into original response
    }
}