package com.kcibald.objects

import com.kcibald.objects.impl.CommentImpl
import com.kcibald.objects.impl.now
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class CommentInterfaceTest {
    val author = User.createDefault(
        "Id",
        "name",
        AttachmentURL.createDefault("url"),
        HTMLContent.createDefault("signature")
    )

    val content = HTMLContent.createDefault("content")

    val ts = now

    val attachment1 = AttachmentURL.createDefault("attachment1")
    val attachment2 = AttachmentURL.createDefault("attachment2")
    val attachments = listOf(
        attachment1,
        attachment2
    )

    val reply1 = Reply.createDefault(
        author, ts, ts, HTMLContent.createDefault("reply1"), listOf()
    )
    val reply2 = Reply.createDefault(
        author, ts, ts, HTMLContent.createDefault("reply1"), listOf()
    )
    val replies = listOf(
        reply1,
        reply2
    )

    val target = CommentImpl(
        author,
        content,
        ts,
        ts,
        attachments,
        replies
    )

    @Test
    fun defaultUpdateTimes() {
        val target = Comment.createDefault(author, content)

        val n = now
        assert(abs(target.updateTimestamp - n) < 5)
    }

    @Test
    fun defaultCreateTimes() {
        val target = Comment.createDefault(author, content)

        val n = now
        assert(abs(target.createTimeStamp - n) < 5)
    }

    val defaultComment = Comment.createDefault(author, content)

    @Test
    fun defaultAttachment() {
        assert(defaultComment.attachments.isEmpty())
    }

    @Test
    fun defaultReplies() {
        assert(defaultComment.replies.isEmpty())
    }

}