package com.example.dao

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.*
import java.util.*
import javax.sql.DataSource

class TestDao : KoinComponent {

    private val dataSource: DataSource by inject()
    private val database = Database.connect(dataSource)
    private val random = Random()

    fun selectCount(): Int {
        return database.from(TestTable).select(count())
            .mapNotNull { r -> r.getInt(1) }.single()
    }

    fun insertWithInputDatabase(database: Database) {
        database.insert(TestTable) {
            set(it.name, "TestUser")
            set(it.age, random.nextInt())
        }
    }

    fun insert() {
        database.insert(TestTable) {
            set(it.name, "TestUser")
            set(it.age, random.nextInt())
        }
    }

}

object TestTable : Table<Nothing>("test") {
    val id = long("id")
    val name = varchar("name")
    val age = int("age")
    val createTime = timestamp("create_time")
}