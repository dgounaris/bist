package com.gounaris.bist.testing.persistence

import com.gounaris.bist.base.use
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import java.sql.DriverManager

// object because this has an object monitor lock
internal object PostgresConnectionManager {
    fun <R> withConnection(
        databaseName: String,
        host: String,
        port: Int,
        user: String,
        password: String,
        vararg exclusiveTo: String = emptyArray(),
        block: (DSLContext) -> R
    ): R {
        return synchronized(this) {
            val url = jdbcUrl(host, port, databaseName)
            val connection = DriverManager.getConnection(url, user, password)
            connection.use {
                DSL.using(DefaultConfiguration().set(SQLDialect.POSTGRES).set(it)).let { dsl ->
                    if (exclusiveTo.isNotEmpty()) withExclusiveConnectionTo(dsl, *exclusiveTo) { block(dsl) }
                    else block(dsl)
                }
            }
        }
    }

    private fun jdbcUrl(host: String, port: Int, db: String) =
        "jdbc:postgresql://$host:$port/$db?ApplicationName=testing-connman"

    private fun <R> withExclusiveConnectionTo(db: DSLContext, vararg dbNames: String, block: (DSLContext) -> R): R {
        val whereDatNames = dbNames.asSequence().map { "datname = '$it'" }.joinToString(" or ")
        // make sure no new connection can be made
        db.execute("update pg_database set datallowconn = false where $whereDatNames;")
        // disconnect everyone else
        db.execute(
            "SELECT pg_terminate_backend(pg_stat_activity.pid) " +
                    "FROM pg_stat_activity " +
                    "WHERE $whereDatNames AND pid <> pg_backend_pid();"
        )

        val result = block(db)

        // allow connections
        db.execute("update pg_database set datallowconn = true where $whereDatNames")

        return result
    }
}