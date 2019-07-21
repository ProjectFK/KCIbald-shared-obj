package com.kcibald.objects.impl

import com.kcibald.objects.Comment
import com.kcibald.objects.User
import com.kcibald.serilization.keyspecs.CommentJsonKeySpec
import com.kcibald.serilization.keyspecs.ContentBasedKeySpec
import com.kcibald.serilization.serializeToJson
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
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
    fun asJson() {
        val json = json {
            obj(
                ContentBasedKeySpec.attachments to attachments,
                ContentBasedKeySpec.author to author.asJson(),
                ContentBasedKeySpec.content to content,
                ContentBasedKeySpec.createTimeStamp to now,
                ContentBasedKeySpec.updateTimestamp to now,
                CommentJsonKeySpec.replies to replies.serializeToJson()
            )
        }
        assertEquals(json, target.asJson())
    }

}