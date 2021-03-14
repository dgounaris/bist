package com.gounaris.bist.client

import com.gounaris.bist.base.objectMapper
import kong.unirest.HttpRequestWithBody
import kong.unirest.Unirest
import kong.unirest.apache.ApacheClient
import kong.unirest.jackson.JacksonObjectMapper
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.TrustAllStrategy
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContexts
import javax.net.ssl.SSLContext

enum class HttpVerb {
    GET, POST
}

data class WebRequest<T>(val path: String, val body: T? = null, val queryParams: Map<String, Any?>? = null)
data class WebResponse<T>(val body: T?, val headers: Map<String, String>, val responseStatus: Int, val responseStatusText: String?)

interface RemoteClient {
    fun <T, R> call(verb: HttpVerb, webRequest: WebRequest<T>, responseClass: Class<R>, token: String? = null): WebResponse<R> where R: Any
    fun <T> call(verb: HttpVerb, webRequest: WebRequest<T>, token: String? = null): WebResponse<String>
    val baseAddress: String
}

class RemoteUnirestClient(override val baseAddress: String, private val enableSSL: Boolean = false) : RemoteClient {
    init {
        Unirest.config().objectMapper = JacksonObjectMapper(objectMapper)
    }

    override fun <T, R : Any> call(
        verb: HttpVerb,
        webRequest: WebRequest<T>,
        responseClass: Class<R>,
        token: String?
    ): WebResponse<R> {
        if (enableSSL) addSSL()

        val path = baseAddress + webRequest.path

        var request = when (verb) {
            HttpVerb.GET -> Unirest.get(path)
            HttpVerb.POST -> Unirest.post(path)
        }

        request = request.header("Content-Type", "application/json")
        if (token != null) request = request.header("Authorization", token)
        if (webRequest.body != null && request is HttpRequestWithBody) request = request.body(webRequest.body)

        webRequest.queryParams?.forEach { item ->
            if (item.value is Collection<*>) {
                (item.value as Collection<*>).forEach { request = request.queryString(item.key, it) }
            } else {
                request = request.queryString(item.key, item.value)
            }
        }

        val response = request.asObject(responseClass)
        return WebResponse(
            response.body,
            response.headers.all().associateBy({ it.name }, { it.value }),
            response.status,
            response.statusText
        )
    }

    override fun <T> call(verb: HttpVerb, webRequest: WebRequest<T>, token: String?): WebResponse<String> {
        if (enableSSL) addSSL()

        val path = baseAddress + webRequest.path

        var request = when (verb) {
            HttpVerb.GET -> Unirest.get(path)
            HttpVerb.POST -> Unirest.post(path)
        }

        request = request.header("Content-Type", "application/json")
        if (token != null) request = request.header("Authorization", token)
        if (webRequest.body != null && request is HttpRequestWithBody) request = request.body(webRequest.body)

        webRequest.queryParams?.forEach { item ->
            if (item.value is Collection<*>) {
                (item.value as Collection<*>).forEach { request = request.queryString(item.key, it) }
            } else {
                request = request.queryString(item.key, item.value)
            }
        }

        val response = request.asString()
        return WebResponse(
            response.body,
            response.headers.all().associateBy({ it.name }, { it.value }),
            response.status,
            response.statusText
        )
    }

    // todo move this to testing, it is insecure to trust this way
    private fun addSSL() {
        val context: SSLContext = SSLContexts.custom()
            .loadTrustMaterial(TrustAllStrategy())
            .build()
        val client = HttpClients.custom()
            .setSSLContext(context)
            .setSSLHostnameVerifier(NoopHostnameVerifier())
            .build()
        Unirest.config().let {
            it.httpClient(ApacheClient.builder(client).apply(it))
        }
    }
}