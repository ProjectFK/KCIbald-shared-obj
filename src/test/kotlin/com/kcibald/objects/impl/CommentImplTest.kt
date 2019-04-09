package com.kcibald.objects.impl

import com.kcibald.objects.*
import com.kcibald.serilization.serializePublicJson
import com.kcibald.serilization.serializeString
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CommentImplTest {

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

    val reply1 = Comment.createDefault(
        author, HTMLContent.createDefault("reply1"), ts, ts
    )
    val reply2 = Comment.createDefault(
        author, HTMLContent.createDefault("reply1"), ts, ts
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
    fun getAuthor() {
        assertEquals(author, target.author)
    }

    @Test
    fun getContent() {
        assertEquals(content, target.content)
    }

    @Test
    fun getCreateTimeStamp() {
        assertEquals(ts, target.createTimeStamp)
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

    @Test
    fun asPublicJson() {
        val json = json {
            obj(
                ContentBased.JsonKeySpec.attachments to attachments.serializeString(),
                ContentBased.JsonKeySpec.author to author.asPublicJson(),
                ContentBased.JsonKeySpec.content to content.asString(),
                ContentBased.JsonKeySpec.createTimeStamp to now,
                ContentBased.JsonKeySpec.updateTimestamp to now,
                Comment.Companion.CommentJsonKeySpec.replies to replies.serializePublicJson()
            )
        }
        assertEquals(json, target.asPublicJson())
    }

}