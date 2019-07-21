package com.kcibald.objects

import com.kcibald.objects.impl.CommentImpl
import com.kcibald.objects.impl.now
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class CommentInterfaceTest {
    val author = User.createDefault(
        "Id",
        "name",
        "name",
        "url",
        "signature"
    )

    val content = "content"

    val ts = now

    val attachment1 = "attachment1"
    val attachment2 = "attachment2"
    val attachments = listOf(
        attachment1,
        attachment2
    )

    val reply1 = Comment.createDefault(
        author, "reply1", ts, ts
    )
    val reply2 = Comment.createDefault(
        author, "reply1", ts, ts
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
        assert(abs(target.updateTimestamp!! - n) < 5)
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