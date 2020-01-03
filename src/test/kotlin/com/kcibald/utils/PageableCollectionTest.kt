package com.kcibald.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PageableCollectionTest {

    @Test
    fun getHasNextPage() {
        val target = PageableCollection.directCollection(listOf(""))
        assertFalse(target.hasNextPage)
    }

    @Test
    fun getCurrentContent() {
        val content = listOf("")
        val target = PageableCollection.directCollection(content)
        assertEquals(content, target.currentContent)
    }

    @Test
    fun getTotalSize() {
        val content = listOf("")
        val target = PageableCollection.directCollection(content)
        assertEquals(content.size, target.totalSize)
    }

    @Test
    fun queryMark() {
        assertNull(PageableCollection.directCollection(listOf("")).queryMark)
    }

    @Test
    fun equals_null() {
        @Suppress("ReplaceCallWithBinaryOperator")
        assertFalse(PageableCollection.directCollection(listOf("")).equals(null))
    }

    @Test
    fun equals_direct_reference() {
        val collection = PageableCollection.directCollection(listOf(""))
        assert(collection == collection)
    }

    @Test
    fun equals_other_class() {
        val collection = PageableCollection.directCollection(listOf(""))
        @Suppress("ReplaceCallWithBinaryOperator")
        assertFalse(collection.equals(""))
    }

    @Test
    fun equals_same_class_different_content() {
        val a = PageableCollection.directCollection(listOf("a"))
        val b = PageableCollection.directCollection(listOf(""))
        assertFalse(a == b)
    }

    @Test
    fun equals_true() {
        val a = PageableCollection.directCollection(listOf(""))
        val b = PageableCollection.directCollection(listOf(""))
        assertTrue(a == b)
    }

    @Test
    fun hashcode_same() {
        val a = PageableCollection.directCollection(listOf(""))
        val b = PageableCollection.directCollection(listOf(""))
        assertEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun hashcode_diff() {
        val a = PageableCollection.directCollection(listOf("a"))
        val b = PageableCollection.directCollection(listOf("b"))
        assertNotEquals(a.hashCode(), b.hashCode())
    }
}