package com.kcibald.objects.impl

import com.kcibald.objects.AttachmentURL
import com.kcibald.objects.ContentBased
import com.kcibald.objects.HTMLContent
import com.kcibald.objects.User
import com.kcibald.serilization.serializeString
import io.vertx.kotlin.core.json.JsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

internal class ReplyImplTest {

    val author = User.createDefault(
        "id",
        "name",
        AttachmentURL.createDefault("url"),
        HTMLContent.createDefault("signature")
    )

    val content = HTMLContent.createDefault("content")

    val now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

    val attachment1 = AttachmentURL.createDefault("attachment0")
    val attachments = listOf(
        attachment1
    )

    val obj = ReplyImpl(
        author,
        now,
        now,
        content,
        attachments
    )

    @Test
    fun getAuthor() {
        assertEquals(author, obj.author)
    }

    @Test
    fun getCreateTimeStamp() {
        assertEquals(now, obj.createTimeStamp)
    }

    @Test
    fun getUpdateTimestamp() {
        assertEquals(now, obj.updateTimestamp)
    }

    @Test
    fun getContent() {
        assertEquals(content, obj.content)
    }

    @Test
    fun getAttachments() {
        assertEquals(attachments, obj.attachments)
    }

    @Test
    fun asPublicJson() {
        val target =
            JsonObject(
                ContentBased.JsonKeySpec.attachments to attachments.serializeString(),
                ContentBased.JsonKeySpec.author to author.asPublicJson(),
                ContentBased.JsonKeySpec.content to content.asString(),
                ContentBased.JsonKeySpec.createTimeStamp to now,
                ContentBased.JsonKeySpec.updateTimestamp to now
            )
        assertEquals(target, obj.asPublicJson())
    }

}