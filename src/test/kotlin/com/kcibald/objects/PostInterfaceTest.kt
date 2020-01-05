package com.kcibald.objects

import com.kcibald.objects.Attachment.Companion.createDefault
import com.kcibald.objects.User.Companion.createDefault
import com.kcibald.objects.impl.now
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class PostInterfaceTest {


    val postDefaultyCreated = Post.createDefault(
        "title",
        createDefault(
            "name",
            "name",
            File.withIdentifier("avatar"), "signature"
        ),
        "content",
        ""
    )

    @Test
    fun defaultUpdateTimes() {

        val target = Post.createDefault(
            "title",
            createDefault(
                "name",
                "name",
                File.withIdentifier("avatar"), "signature"
            ),
            "content",
            ""
        )

        assertEquals(null, target.updateTimestamp)
    }

    @Test
    fun setUpdateTimes() {

        val expected = now

        val target = Post.createDefault(
            "title",
            createDefault(
                "name",
                "name",
                File.withIdentifier("avatar"), "signature"
            ),
            "content",
            "",
            updateTimestamp = expected
        )

        assertEquals(expected, target.updateTimestamp)
    }

    @Test
    fun defaultCreateTimes() {

        val target = Post.createDefault(
            "title",
            createDefault(
                "name",
                "name",
                File.withIdentifier("avatar"), "signature"
            ),
            "content",
            urlKey = "",
            parentRegionKey = ""
        )

        val n = now
        assert(abs(target.createTimestamp - n) < 5)
    }

    @Test
    fun setCreateTimes() {

        val expected = now

        val target = Post.createDefault(
            "title",
            createDefault(
                "name",
                "name",
                File.withIdentifier("avatar"), "signature"
            ),
            "content",
            urlKey = "",
            parentRegionKey = "",
            createTimeStamp = expected
        )

        assertEquals(expected, target.createTimestamp)
    }

    @Test
    fun defaultAttachment() {
        assert(postDefaultyCreated.attachments.isEmpty())
    }


    @Test
    fun set_attachment() {
        val attachments = listOf(createDefault(File.withIdentifier(""), ""))
        val target = Post.createDefault(
            "title",
            createDefault(
                "name",
                "name",
                File.withIdentifier("avatar"), "signature"
            ),
            "content",
            urlKey = "",
            parentRegionKey = "",
            attachments = attachments
        )

        assertEquals(attachments, target.attachments)
    }

    @Test
    fun defaultComment() {
        assert(postDefaultyCreated.comments.totalSize == 0)
    }

    @Test
    fun json() {
        val target = Post.createDefault(
            "title",
            createDefault("username", "username", File.withIdentifier(""), "sig"),
            "content",
            "parent-reg"
        )
        val json = Post.vertxGenToJson(target)
        println(json)
        assertEquals(target, Post.vertxGenFromJson(json))
    }

}