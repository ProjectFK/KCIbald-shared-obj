package com.kcibald.objects.impl

import com.kcibald.objects.Attachment
import com.kcibald.objects.Comment
import com.kcibald.objects.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CommentImplTest {

    val author = User.createDefault(
        "Id",
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

    val id = 3

    val target = CommentImpl(
        id,
        author,
        content,
        ts,
        ts,
        attachments,
        replies
    )

    @Test
    fun getId() {
        assertEquals(id, target.id)
    }

    @Test
    fun getAuthor() {
        assertEquals(author, target.author)
    }

    @Test
    fun getContent() {
        assertEquals(content, target.content)
    }

    @Test
    fun getCreateTimeStamp() {
        assertEquals(ts, target.createTimestamp)
    }

    @Test
    fun getUpdateTimestamp() {
        assertEquals(ts, target.updateTimestamp)
    }

    @Test
    fun getAttachments() {
        assertEquals(attachments, target.attachments)
    }

    @Test
    fun getReplies() {
        assertEquals(replies, target.replies)
    }

}