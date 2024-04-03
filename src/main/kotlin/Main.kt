package org.example

import org.jooq.SQLDialect
import org.jooq.impl.DSL
import test.jooq.generated.tables.references.TEST
import java.sql.DriverManager

fun main() {
    val userName = "postgres"
    val password = "postgres"
    val url = "jdbc:postgres://localhost:5432/test"

    // Connection is the only JDBC resource that we need
    // PreparedStatement and ResultSet are handled by jOOQ, internally
    try {
        DriverManager.getConnection(url, userName, password).use { conn ->
            val ctx = DSL.using(conn, SQLDialect.POSTGRES)
            ctx.selectFrom(TEST)
                .fetchMany()
        }
    } // For the sake of this tutorial, let's keep exception handling simple
    catch (e: Exception) {
        e.printStackTrace()
    }
}