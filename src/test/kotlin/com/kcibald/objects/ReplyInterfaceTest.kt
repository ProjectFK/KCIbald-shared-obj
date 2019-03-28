package com.kcibald.objects

import com.kcibald.objects.impl.now
import org.junit.jupiter.api.Test
import java.lang.Math.abs

internal class ReplyInterfaceTest {
    val author = User.createDefault(
        "id",
        "name",
        AttachmentURL.createDefault("avatar"),
        HTMLContent.createDefault("signature")
    )
    val content = HTMLContent.createDefault("content")

    val target = Reply.createDefault(
        author,
        content = content
    )

    @Test
    fun defaultReplyCreationTime() {
        val t = Reply.createDefault(
            author, content = content
        )
        val n = now
        assert(abs(t.createTimeStamp - n) < 5)
    }

    @Test
    fun defaultReplyUpdateTime() {
        val t = Reply.createDefault(
            author, content = content
        )
        val n = now
        assert(abs(t.updateTimestamp - n) < 5)
    }

    @Test
    fun defaultReplyAttachment() {
        assert(target.attachments.isEmpty())
    }

}