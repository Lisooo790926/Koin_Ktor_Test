package com.example.services

import com.example.dao.TestDao
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declare
import org.koin.test.mock.declareMock
import kotlin.test.assertEquals

class TestServiceTest : KoinTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single { TestDao() }
                single { TestService() }
            }
        )
    }

    lateinit var testDao: TestDao
    lateinit var testService: TestService

    @Before
    fun setup() {
        testDao = declareMock()
        testService = spyk(recordPrivateCalls = true)
    }

    @Test
    fun test() {
        every { testDao.selectCount() } returns 0
        every { testService["testPrivate"]() } returns 1

        testService.test()
        verify(exactly = 1) {
            testService["testPrivate"]()
        }
    }

    @Test
    fun test2() {
        every { testDao.selectCount() } returns 0
        testService.test2()
        verify(exactly = 1) { testDao.selectCount() }
        assertEquals(3, testService.test2())
    }
}