package com.kcibald.objects

import com.kcibald.objects.impl.CommentImpl
import com.kcibald.objects.impl.now
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class CommentInterfaceTest {
    val author = User.createDefault(
        "name",
        "name",
        "url",
        "signature"
    )

    val content = "content"

    val ts = now

    val attachment1 = Attachment.createDefault("attachment1", "attachment_name1")
    val attachment2 = Attachment.createDefault("attachment2", "attachment_name2")
    val attachments = listOf(
        attachment1,
        attachment2
    )

    val reply1 = Comment.createDefault(
        1, author, "reply1", ts, ts
    )
    val reply2 = Comment.createDefault(
        2, author, "reply1", ts, ts
    )
    val replies = listOf(
        reply1,
        reply2
    )

    val target = CommentImpl(
        3,
        author,
        content,
        ts,
        ts,
        attachments,
        replies
    )

    @Test
    fun defaultUpdateTimes() {
        val target = Comment.createDefault(1, author, content)

        assertEquals(null, target.updateTimestamp)
    }

    @Test
    fun defaultCreateTimes() {
        val target = Comment.createDefault(1, author, content)

        val n = now
        assert(abs(target.createTimestamp - n) < 5)
    }

    val defaultComment = Comment.createDefault(1, author, content)

    @Test
    fun defaultAttachment() {
        assert(defaultComment.attachments.isEmpty())
    }

    @Test
    fun defaultReplies() {
        assert(defaultComment.replies.isEmpty())
    }

}