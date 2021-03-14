package com.gounaris.bist

import com.gounaris.bist.testing.persistence.accessors.PostgresDBAccessor
import com.gounaris.bist.testing.persistence.restorers.PostgresDBRestorer
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource

class GlobalTestSetup : BeforeAllCallback, CloseableResource {

    private companion object {
        private var started = false
    }

    override fun beforeAll(context: ExtensionContext) {
        if (!started) {
            started = true

            var dbHost = "localhost"
            var dbPort = 5432
            var serverHost = "localhost"
            var serverPort = 8080

            if (!IntegrationTestBase.externalApp) {
                IntegrationTestBase.db.start()
                IntegrationTestBase.server.start()

                dbHost = IntegrationTestBase.db.host
                dbPort = IntegrationTestBase.db.getMappedPort(dbPort)
                serverHost = IntegrationTestBase.db.host
                serverPort = IntegrationTestBase.server.getMappedPort(serverPort)
            }

            IntegrationTestBase.wiremockServer.start()

            IntegrationTestBase.restorer = PostgresDBRestorer(
                host = dbHost,
                port = dbPort
            )

            IntegrationTestBase.dbAccessor = PostgresDBAccessor(
                host = dbHost,
                port = dbPort
            )

            IntegrationTestBase.serverHostAndPort =
                "http://$serverHost:$serverPort"

            context.root.getStore(GLOBAL).put("globalTestSetup", this)
        }
    }

    override fun close() {
        IntegrationTestBase.wiremockServer.stop()

        // give time to app to finish all running scheduled jobs
        Thread.sleep(5000)

        if (!IntegrationTestBase.externalApp) {
            IntegrationTestBase.server.stop()
            IntegrationTestBase.db.stop()
        }
    }
}