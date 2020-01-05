package com.kcibald.objects

import com.kcibald.objects.Attachment.Companion.createDefault
import com.kcibald.objects.User.Companion.createDefault
import com.kcibald.objects.impl.CommentImpl
import com.kcibald.objects.impl.now
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class CommentInterfaceTest {
    val author = createDefault(
        "name",
        "name",
        File.withIdentifier("url"), "signature"
    )

    val content = "content"

    val ts = now

    val attachment1 = createDefault(File.withIdentifier("attachment1"), "attachment_name1")
    val attachment2 = createDefault(File.withIdentifier("attachment2"), "attachment_name2")
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
    fun updateTimes_set() {
        val expected = now
        val target = Comment.createDefault(1, author, content, updateTimestamp = expected)
        assertEquals(expected, target.updateTimestamp)
    }

    @Test
    fun defaultCreateTimes() {
        val target = Comment.createDefault(1, author, content)

        val n = now
        assert(abs(target.createTimestamp - n) < 5)
    }

    @Test
    fun attachment_set() {
        val attachments = listOf(
            attachment1
        )
        val comment = Comment.createDefault(1, author, content, attachments = attachments)
        assertEquals(attachments, comment.attachments)
    }

    @Test
    fun attachment_default() {
        val comment = Comment.createDefault(1, author, content)
        assertEquals(listOf<Attachment>(), comment.attachments)
    }

    @Test
    fun defaultReplies() {
        val defaultComment = Comment.createDefault(1, author, content)
        assert(defaultComment.replies.isEmpty())
    }

    @Test
    fun json() {
        val json = Comment.vertxGenToJson(target)
        assertEquals(target, Comment.vertxGenFromJson(json))
    }

}