package com.kcibald.objects

import com.kcibald.objects.impl.now
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class PostInterfaceTest {


    val postDefaultyCreated = Post.createDefault(
        "id",
        "title",
        User.createDefault(
            "id",
            "name",
            AttachmentURL.createDefault("avatar"),
            HTMLContent.createDefault("signature")
        ),
        HTMLContent.createDefault("content")
    )

    @Test
    fun defaultUpdateTimes() {

        val target = Post.createDefault(
            "id",
            "title",
            User.createDefault(
                "id",
                "name",
                AttachmentURL.createDefault("avatar"),
                HTMLContent.createDefault("signature")
            ),
            HTMLContent.createDefault("content")
        )

        val n = now
        assert(abs(target.updateTimestamp - n) < 5)
    }

    @Test
    fun defaultCreateTimes() {

        val target = Post.createDefault(
            "id",
            "title",
            User.createDefault(
                "id",
                "name",
                AttachmentURL.createDefault("avatar"),
                HTMLContent.createDefault("signature")
            ),
            HTMLContent.createDefault("content")
        )

        val n = now
        assert(abs(target.createTimeStamp - n) < 5)
    }

    @Test
    fun defaultAttachment() {
        assert(postDefaultyCreated.attachments.isEmpty())
    }

    @Test
    fun defaultComment() {
        assert(postDefaultyCreated.comments.isEmpty())
    }
}