package com.kcibald.utils

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DirectCollectionTest {

    @Test
    fun getHasNextPage() {
        val target = DirectCollection(listOf(""))
        assertFalse(target.hasNextPage)
    }

    @Test
    fun getCurrentContent() {
        val content = listOf("")
        val target = DirectCollection(content)
        assertEquals(content, target.currentContent)
    }

    @Test
    fun getTotalSize() {
        val content = listOf("")
        val target = DirectCollection(content)
        assertEquals(content.size, target.totalSize)
    }

    @Test
    fun getNextPage() = runBlocking {
        assertNull(DirectCollection(listOf("")).getNextPage())
    }

    @Test
    fun queryMark() {
        assertNull(DirectCollection(listOf("")).queryMark)
    }
}