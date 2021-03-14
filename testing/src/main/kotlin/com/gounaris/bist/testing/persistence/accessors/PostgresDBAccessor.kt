package com.gounaris.bist.testing.persistence.accessors

import com.gounaris.bist.testing.persistence.DBAccessor
import com.gounaris.bist.testing.persistence.PostgresConnectionManager

class PostgresDBAccessor(
    private val databaseName: String = "postgres",
    private val host: String = "localhost",
    private val port: Int = 5432,
    private val user: String = "postgres",
    private val password: String = "password"
) : DBAccessor {
    override fun singleFromTable(table: String): Boolean =
        PostgresConnectionManager.withConnection(
            databaseName, host, port, user, password
        ) {
            single("select * from $table")
        }

    override fun <T> fetchAllMatchingFromTable(table: String, clazz: Class<T>, matchFilter: (T) -> Boolean) : List<T> =
        PostgresConnectionManager.withConnection(
            databaseName, host, port, user, password
        ) {
            fetchAllMatching("select * from $table", clazz, matchFilter)
        }

    private fun single(sql: String): Boolean =
        PostgresConnectionManager.withConnection(
            databaseName, host, port, user, password
        ) {
            it.resultQuery(sql).fetch().size == 1
        }

    private fun <T> fetchAllMatching(sql: String, clazz: Class<T>, matchFilter: (T) -> Boolean) : List<T> =
        PostgresConnectionManager.withConnection(
            databaseName, host, port, user, password
        ) {
            fetchAs(sql, clazz).filter { resultItem -> matchFilter(resultItem) }
        }

    private fun <T> fetchAs(sql: String, clazz: Class<T>): List<T> =
        PostgresConnectionManager.withConnection(
            databaseName, host, port, user, password
        ) {
            it.resultQuery(sql).fetchInto(clazz)
        }
}