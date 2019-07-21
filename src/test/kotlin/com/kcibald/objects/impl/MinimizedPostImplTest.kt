package com.kcibald.objects.impl

import com.kcibald.objects.Timestamp
import com.kcibald.objects.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MinimizedPostImplTest {

    val id = "kxkjalkdjf"
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

    val target = MinimizedPostImpl(
        id,
        title,
        urlKey,
        parentRegionUrlKey,
        commentCount,
        author,
        createTimeStamp,
        updateTimeStamp,
        content
    )

    @Test
    fun id() {
        assertEquals(id, target.id)
    }

    @Test
    fun title() {
        assertEquals(title, target.title)
    }

    @Test
    fun urlKey() {
        assertEquals(urlKey, target.urlKey)
    }

    @Test
    fun parentRegionKey() {
        assertEquals(parentRegionUrlKey, target.parentRegionUrlKey)
    }

    @Test
    fun commentCount() {
        assertEquals(commentCount, target.commentCount)
    }

    @Test
    fun author() {
        assertEquals(author, target.author)
    }

    @Test
    fun createTimeStamp() {
        assertEquals(createTimeStamp, target.createTimeStamp)
    }

    @Test
    fun updateTimeStamp() {
        assertEquals(updateTimeStamp, target.updateTimestamp)
    }

    @Test
    fun content() {
        assertEquals(content, target.content)
    }


}