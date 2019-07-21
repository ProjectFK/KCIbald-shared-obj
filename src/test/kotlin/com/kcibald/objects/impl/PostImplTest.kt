package com.kcibald.objects.impl

import com.kcibald.objects.Comment
import com.kcibald.objects.Post
import com.kcibald.objects.User
import com.kcibald.serilization.keyspecs.ContentBasedKeySpec
import com.kcibald.serilization.keyspecs.PostJsonKeySpec
import com.kcibald.serilization.serializeToJson
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PostImplTest {

    val author = User.createDefault(
        "userId",
        "user name",
        "user_name",
        "avatar",
        "signature"
    )

    val id = "post-id"
    val title = "title"
    val content = "content"
    val ts = now

    val attachment1 = "attachment1"
    val attachment2 = "attachment2"
    val attachments = listOf(
        attachment1,
        attachment2
    )

    val comment1 = Comment.createDefault(
        author,
        "content1",
        ts,
        ts,
        listOf(),
        listOf()
    )
    val comment2 = Comment.createDefault(
        author,
        "content2",
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
    fun asJson() {
        val json = json {
            obj(
                ContentBasedKeySpec.author to author.asJson(),
                ContentBasedKeySpec.content to content,
                ContentBasedKeySpec.createTimeStamp to ts,
                ContentBasedKeySpec.updateTimestamp to ts,
                ContentBasedKeySpec.attachments to attachments,
                PostJsonKeySpec.comments to comments.serializeToJson(),
                PostJsonKeySpec.id to id
            )
        }
        assertEquals(json, target.asJson())
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