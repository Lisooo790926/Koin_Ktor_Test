package com.example

import com.example.dao.TestDao
import com.example.plugins.configureRouting
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import javax.sql.DataSource

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {

        // load the database basic information
        val config: ApplicationConfig = ApplicationConfig("application.conf").config("testDb")

        install(Koin) {
            modules(
                module {
                    single { TestDao() }
                    single { buildDataSource(config) }
                }
            )
        }

        configureRouting()

    }.start(wait = true)
}

fun buildDataSource(config: ApplicationConfig): DataSource {
    val hikariConfig = HikariConfig().apply {
        poolName = config.propertyOrNull("dataSource.poolName")?.getString()
        driverClassName = config.propertyOrNull("driverClassName")?.getString()
        jdbcUrl = config.propertyOrNull("dataSource.url")?.getString()
        username = config.propertyOrNull("dataSource.username")?.getString()
        password = config.propertyOrNull("dataSource.password")?.getString()
        minimumIdle = 1
        maximumPoolSize =
            config.propertyOrNull("dataSource.maxPoolSize")?.getString()?.toInt() ?: 30
        transactionIsolation = "TRANSACTION_READ_COMMITTED"
        connectionTestQuery = "SELECT 1"
    }
    return HikariDataSource(hikariConfig)
}
