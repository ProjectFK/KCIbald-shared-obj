package com.kcibald.objects.impl

import com.kcibald.objects.Attachment.Companion.createDefault
import com.kcibald.objects.Comment
import com.kcibald.objects.File
import com.kcibald.objects.Post
import com.kcibald.objects.User.Companion.createDefault
import com.kcibald.utils.toURLKey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class PostImplTest {

    val author = createDefault(
        "user name",
        "user_name",
        File.withIdentifier("avatar"), "signature"
    )

    val id = "post-id"
    val title = "title"
    val content = "content"
    val ts = now

    val attachment1 = createDefault(File.withIdentifier("attachment1"), "attachment_name1")
    val attachment2 = createDefault(File.withIdentifier("attachment2"), "attachment_name2")
    val attachments = listOf(
        attachment1,
        attachment2
    )
    val comment1 = Comment.createDefault(
        1,
        author,
        "content1",
        ts,
        ts,
        listOf(),
        listOf()
    )
    val comment2 = Comment.createDefault(
        2,
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

    val urlKey = title.toURLKey()

    val parentRegionKey = ""

    val target = Post.createDefault(
        title,
        author,
        content,
        parentRegionKey,
        ts,
        ts,
        attachments,
        comments,
        urlKey
    )

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
    fun getComments() {
        val comm = target.comments
        assertFalse(comm.hasNextPage)
        assertEquals(comments, comm.currentContent)
    }

    @Test
    fun getUrlKey() {
        assertEquals(urlKey, target.urlKey)
    }

    @Test
    fun getCommentSize() {
        assertEquals(comments.size, target.commentCount)
    }

    @Test
    fun sourceRegionURLKey() {
        assertEquals(parentRegionKey, target.sourceRegionURLKey)
    }

}