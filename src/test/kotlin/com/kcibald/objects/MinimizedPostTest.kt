package com.kcibald.objects

import com.kcibald.objects.User.Companion.createDefault
import com.kcibald.objects.impl.MinimizedPostImpl
import com.kcibald.objects.impl.now
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MinimizedPostTest {

    @Test
    fun createDefault_without_creation_timestamp() {
        val title = "title"
        val urlKey = "title"
        val parentRegionUrlKey = "DP"
        val commentCount = 1
        val author = createDefault(
            "name",
            "name",
            File.withIdentifier("url"), "signature"
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
                content,
                author,
                commentCount,
                parentRegionUrlKey,
                createTimeStamp = createTimeStamp
            )
        )
    }

    @Test
    fun createDefault_with_default_creationTimeStamp() {
        val title = "title"
        val parentRegionUrlKey = "DP"
        val commentCount = 1
        val author = createDefault(
            "name",
            "name",
            File.withIdentifier("url"), "signature"
        )
        val content = "blah"

        val created = MinimizedPost.createDefault(
            title,
            content,
            author,
            commentCount,
            parentRegionUrlKey
        )

        assert(
            (now.minus(created.createTimestamp)) < 5
        )
    }

    @Test
    fun createDefault_manual() {
        val title = "title"
        val urlKey = "title"
        val parentRegionUrlKey = "DP"
        val commentCount = 1
        val author = createDefault(
            "name",
            "name",
            File.withIdentifier("url"), "signature"
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
                content,
                author,
                commentCount,
                parentRegionUrlKey,
                urlKey = urlKey,
                createTimeStamp = createTimeStamp,
                updateTimestamp = updateTimeStamp
            )
        )
    }

    @Test
    fun json() {
        val title = "title"
        val urlKey = "title"
        val parentRegionUrlKey = "DP"
        val commentCount = 1
        val author = createDefault(
            "name",
            "name",
            File.withIdentifier("url"), "signature"
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

        assertEquals(expected, MinimizedPost.vertxGenFromJson(MinimizedPost.vertxGenToJson(expected)))
    }

}