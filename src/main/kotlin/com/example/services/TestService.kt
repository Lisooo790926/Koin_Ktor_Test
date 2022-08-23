package com.example.services

import com.example.dao.TestDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TestService : KoinComponent {

    private val testDao: TestDao by inject()

    private fun testPrivate(): Int {
        return 3
    }

    fun test() {
        println(testPrivate() + testDao.selectCount())
    }

    fun test2(): Int {
        return testPrivate() + testDao.selectCount()
    }

}


