package com.gounaris.bist.testing.persistence.restorers

import com.gounaris.bist.testing.persistence.DBRestorer
import com.gounaris.bist.testing.persistence.PostgresConnectionManager

class PostgresDBRestorer(
    private val databaseName: String = "postgres",
    host: String = "localhost",
    port: Int = 5432,
    user: String = "postgres",
    password: String = "password"
) : DBRestorer {
    private val internalRestorer: PostgresInternalDBRestorer = PostgresInternalDBRestorer(
        host, port, user, password
    )

    override fun takeSnapshot() = internalRestorer.copy(databaseName, databaseName.toSnapshotName())

    override fun restore() = internalRestorer.copy(databaseName.toSnapshotName(), databaseName)

    private fun String.toSnapshotName() = this + "_snapshot"
}

internal class PostgresInternalDBRestorer(
    private val host: String,
    private val port: Int,
    private val user: String,
    private val password: String
) {
    fun copy(from: String, to: String) {
        PostgresConnectionManager.withConnection(
            // for some weird reason, the args below only work in the "to, from" order
            from, host, port, user, password, to, from
        ) {
            it.execute("drop database if exists $to;")
            it.execute("create database $to template $from;")
        }
    }
}