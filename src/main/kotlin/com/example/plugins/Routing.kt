package com.example.plugins

import com.example.dao.TestDao
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import org.ktorm.database.Database

fun Application.configureRouting() {

    val testDao : TestDao by inject()
    val database: Database by inject()

    routing {
        get("/select-count") {
            val result = testDao.selectCount()
            call.respondText ("result = $result", contentType = ContentType.Text.Plain)
        }

        get("/all") {
            val result = testDao.getAll()
            call.respondText ("result = $result", contentType = ContentType.Text.Plain)
        }

        // ideally this scenario should use the same transaction
        get("/dao-database/use-transaction-to-insert-data") {
            database.useTransaction {
                testDao.insert()
                testDao.insert()
                testDao.insert()
                throw Exception("To rollback whole transaction")
            }
        }

        get("/same-database/use-transaction-to-insert-data") {
            database.useTransaction {
                testDao.insertWithInputDatabase(database)
                testDao.insertWithInputDatabase(database)
                testDao.insertWithInputDatabase(database)
                throw Exception("To rollback whole transaction")
            }
        }
    }
}
