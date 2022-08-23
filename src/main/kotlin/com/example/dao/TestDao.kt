package com.example.dao

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.time.Instant
import java.util.*

class TestDao : KoinComponent {

    private val database: Database by inject()
    private val random = Random()

    fun selectCount(): Int {
        return database.from(TestTable).select(count())
            .mapNotNull { r -> r.getInt(1) }.single()
    }

    fun getAll(): List<TestEntity> {
        return database.from(TestTable).select()
            .mapNotNull { TestTable.createEntity(it) }
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

interface TestEntity : Entity<TestEntity> {
    val id: Int
    val name: String
    val age: Int
    val createTime: Instant
    companion object: Entity.Factory<TestEntity>()
}

data class InsertRequest (
    val id: Int?,
    val name: String,
    val age: Int,
    val createTime: Instant?,
) {
    companion object {
        fun from(request: InsertRequest) : TestEntity {
            return TestEntity {
                request.name

            }
        }
    }
}