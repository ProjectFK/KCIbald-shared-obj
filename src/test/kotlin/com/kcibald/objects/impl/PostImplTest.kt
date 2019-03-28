package com.kcibald.objects.impl

import com.kcibald.objects.*
import com.kcibald.serilization.serializePublicJson
import com.kcibald.serilization.serializeString
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PostImplTest {

    val author = User.createDefault(
        "userId",
        "user name",
        AttachmentURL.createDefault("avatar"),
        HTMLContent.createDefault("signature")
    )

    val id = "post-id"
    val title = "title"
    val content = HTMLContent.createDefault("content")
    val ts = now

    val attachment1 = AttachmentURL.createDefault("attachment1")
    val attachment2 = AttachmentURL.createDefault("attachment2")
    val attachments = listOf(
        attachment1,
        attachment2
    )

    val comment1 = Comment.createDefault(
        author,
        HTMLContent.createDefault("content1"),
        ts,
        ts,
        listOf(),
        listOf()
    )
    val comment2 = Comment.createDefault(
        author,
        HTMLContent.createDefault("content2"),
        ts,
        ts,
        listOf(),
        listOf()
    )
    val comments = listOf(
        comment1,
        comment2
    )

    val target = Post.createDefault(
        id, title, author, content, ts, ts, attachments, comments
    )


    @Test
    fun asPublicJson() {
        val json = json {
            obj(
                ContentBased.JsonKeySpec.author to author.asPublicJson(),
                ContentBased.JsonKeySpec.content to content.asString(),
                ContentBased.JsonKeySpec.createTimeStamp to ts,
                ContentBased.JsonKeySpec.updateTimestamp to ts,
                ContentBased.JsonKeySpec.attachments to attachments.serializeString(),
                Post.PostJsonKeySpec.comments to comments.serializePublicJson(),
                Post.PostJsonKeySpec.id to id
            )
        }
        assertEquals(json, target.asPublicJson())
    }

    @Test
    fun getId() {
        assertEquals(id, target.id)
    }

    @Test
    fun getTitle() {
        assertEquals(title, target.title)
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
    fun getComments() {
        assertEquals(comments, target.comments)
    }

}