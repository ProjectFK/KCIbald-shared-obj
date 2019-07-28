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
    fun getNextPage_withPara() = runBlocking {
        assertNull(DirectCollection(listOf("")).getNextPage(2000, 200))
    }

    @Test
    fun queryMark() {
        assertNull(DirectCollection(listOf("")).queryMark)
    }

    @Test
    fun equals_null() {
        @Suppress("ReplaceCallWithBinaryOperator")
        assertFalse(DirectCollection(listOf("")).equals(null))
    }

    @Test
    fun equals_direct_reference() {
        val collection = DirectCollection(listOf(""))
        assert(collection == collection)
    }

    @Test
    fun equals_other_class() {
        val collection = DirectCollection(listOf(""))
        @Suppress("ReplaceCallWithBinaryOperator")
        assertFalse(collection.equals(""))
    }

    @Test
    fun equals_same_class_different_content() {
        val a = DirectCollection(listOf("a"))
        val b = DirectCollection(listOf(""))
        assertFalse(a == b)
    }

    @Test
    fun equals_true() {
        val a = DirectCollection(listOf(""))
        val b = DirectCollection(listOf(""))
        assertTrue(a == b)
    }

    @Test
    fun hashcode_same() {
        val a = DirectCollection(listOf(""))
        val b = DirectCollection(listOf(""))
        assertEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun hashcode_diff() {
        val a = DirectCollection(listOf("a"))
        val b = DirectCollection(listOf("b"))
        assertNotEquals(a.hashCode(), b.hashCode())
    }
}