package com.kcibald.objects

import com.kcibald.objects.impl.MinimizedPostImpl
import com.kcibald.objects.impl.now
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MinimizedPostTest {

    @Test
    fun createDefault() {
        val title = "title"
        val urlKey = "title"
        val parentRegionUrlKey = "DP"
        val commentCount = 1
        val author = User.createDefault(
            "Id",
            "name",
            "name",
            "url",
            "signature"
        )
        val createTimeStamp = now
        val updateTimeStamp: Timestamp? = null
        val content = "blah"

        val expected = MinimizedPostImpl(
            title,
            urlKey,
            parentRegionUrlKey,
            commentCount,
            author,
            createTimeStamp,
            updateTimeStamp,
            content
        )

        assertEquals(
            expected,
            MinimizedPost.createDefault(
                title,
                urlKey,
                content,
                author,
                commentCount,
                parentRegionUrlKey,
                createTimeStamp
            )
        )
    }

    @Test
    fun createDefault_with_default_para_updateTimeStamp() {
        val title = "title"
        val urlKey = "title"
        val parentRegionUrlKey = "DP"
        val commentCount = 1
        val author = User.createDefault(
            "Id",
            "name",
            "name",
            "url",
            "signature"
        )
        val content = "blah"

        val created = MinimizedPost.createDefault(
            title,
            urlKey,
            content,
            author,
            commentCount,
            parentRegionUrlKey
        )

        assertEquals(
            null, created.updateTimestamp
        )
    }

    @Test
    fun createDefault_with_default_para_creationTimeStamp() {
        val title = "title"
        val urlKey = "title"
        val parentRegionUrlKey = "DP"
        val commentCount = 1
        val author = User.createDefault(
            "Id",
            "name",
            "name",
            "url",
            "signature"
        )
        val content = "blah"

        val created = MinimizedPost.createDefault(
            title,
            urlKey,
            content,
            author,
            commentCount,
            parentRegionUrlKey
        )

        assert(
            (now.minus(created.createTimestamp)) < 5
        )
    }

}