package com.kcibald.utils

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PageableCollectionTest {

    @Test
    fun defaultAmount() = runBlocking {
        val expect = 5
        var trap: Int? = null

        val target = object : PageableCollection<String>(false, listOf()) {
            override val defaultAmount: Int
                get() = expect

            override suspend fun getNextPage(amount: Int, skip: Int): PageableCollection<String>? {
                trap = amount
                return null
            }

        }

        target.getNextPage()

        assertEquals(expect, trap)
    }

}