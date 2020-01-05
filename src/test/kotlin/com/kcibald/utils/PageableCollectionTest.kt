package com.kcibald.utils

import com.kcibald.objects.invoke
import com.kcibald.utils.PageableCollectionTypeErased.Companion.vertxGenFromJson
import com.kcibald.utils.PageableCollectionTypeErased.Companion.vertxGenToJson
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
    fun eraseType() {
        val origin = PageableCollection.multiPageCollection(
            listOf(""),
            "query",
            20
        )
        val erased = origin.eraseType()
        assertEquals(origin.currentContent, erased.currentContent)
        assertEquals(origin.hasNextPage, erased.hasNextPage)
        assertEquals(origin.queryMark, erased.queryMark)
        assertEquals(origin.totalSize, erased.totalSize)
    }

    @Test
    fun eraseTypeJson() {
        val origin = PageableCollection.multiPageCollection(
            listOf(""),
            "query",
            20
        ).eraseType()

        val result = vertxGenFromJson(vertxGenToJson(origin))
        assertEquals(origin.totalSize, result.totalSize)
        assertEquals(origin.queryMark, result.queryMark)
        assertEquals(origin.hasNextPage, result.hasNextPage)
        assertEquals(origin.currentContent, result.currentContent)
    }

    @Test
    fun invalid_size_total_size_too_small() {
        assertThrows(IllegalArgumentException::class.java) {
            val listWithSizeOfThree = listOf("", "", "")
            PageableCollection.multiPageCollection(listWithSizeOfThree, "", 2)
        }
    }

    @Test
    fun delegateToString() {
        val internal = "hi"
        val testObj = object: PageableCollection<String> {
            override val hasNextPage: Boolean
                get() = fail()
            override val currentContent: List<String>
                get() = fail()
            override val queryMark: String?
                get() = fail()
            override val totalSize: Int
                get() = fail()

            override fun toString(): String {
                return internal
            }
        }.eraseType()

        assertEquals("PageableCollectionTypeErasedDelegator(${internal})", testObj.toString())
    }

}