package com.gounaris.bist

import com.gounaris.bist.testing.containers.DockerTestContainer
import com.gounaris.bist.testing.persistence.accessors.PostgresDBAccessor
import com.gounaris.bist.testing.persistence.restorers.PostgresDBRestorer
import com.github.tomakehurst.wiremock.WireMockServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.images.builder.ImageFromDockerfile
import java.nio.file.Paths

@ExtendWith(GlobalTestSetup::class)
internal open class IntegrationTestBase {
    companion object {
        @JvmStatic
        internal val externalApp: Boolean = false
        @JvmStatic
        internal var wiremockPort: Int = 8081
        @JvmStatic
        internal val wiremockServer = WireMockServer(wiremockPort)
        @JvmStatic
        internal val network = Network.newNetwork()
        @JvmStatic
        internal lateinit var restorer: PostgresDBRestorer
        @JvmStatic
        internal lateinit var dbAccessor: PostgresDBAccessor
        @JvmStatic
        internal val db: GenericContainer<*> = DockerTestContainer.defineGenericContainer("postgres:12-alpine")
            .withEnv("POSTGRES_PASSWORD", "password")
            .withNetwork(network)
            .withNetworkAliases("db")
            .withExposedPorts(5432)
        @JvmStatic
        internal val server: GenericContainer<*> = DockerTestContainer.defineGenericContainer(
            ImageFromDockerfile().withDockerfile(Paths.get("./Dockerfile"))
        ).withNetwork(network)
            .withNetworkAliases("server")
            .withExposedPorts(8080, 5005)
            .withEnv("SPRING_PROFILES_ACTIVE", "test")
            .also {
                it.extraHosts = listOf("host.docker.internal:host-gateway")
            }
        @JvmStatic
        internal lateinit var serverHostAndPort: String
    }

    @BeforeEach
    fun setup() {
        restorer.takeSnapshot()
        // give time to app to connect to db again
        Thread.sleep(3000)
    }

    @AfterEach
    fun teardown() {
        wiremockServer.resetToDefaultMappings()
        restorer.restore()
    }

}