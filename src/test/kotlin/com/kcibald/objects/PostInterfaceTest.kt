package com.kcibald.objects

import com.kcibald.objects.impl.now
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class PostInterfaceTest {


    val postDefaultyCreated = Post.createDefault(
        "title",
        User.createDefault(
            "name",
            "name",
            "avatar",
            "signature"
        ),
        "content",
        ""
    )

    @Test
    fun defaultUpdateTimes() {

        val target = Post.createDefault(
            "title",
            User.createDefault(
                "name",
                "name",
                "avatar",
                "signature"
            ),
            "content",
            ""
        )

        assertEquals(null, target.updateTimestamp)
    }

    @Test
    fun defaultCreateTimes() {

        val target = Post.createDefault(
            "title",
            User.createDefault(
                "name",
                "name",
                "avatar",
                "signature"
            ),
            "content",
            urlKey = "",
            parentRegionKey = ""
        )

        val n = now
        assert(abs(target.createTimestamp - n) < 5)
    }

    @Test
    fun defaultAttachment() {
        assert(postDefaultyCreated.attachments.isEmpty())
    }

    @Test
    fun defaultComment() {
        assert(postDefaultyCreated.comments.totalSize == 0)
    }

}