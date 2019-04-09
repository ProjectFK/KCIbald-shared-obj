package com.kcibald.objects

import com.kcibald.objects.impl.CommentImpl
import com.kcibald.objects.impl.now
import com.kcibald.serilization.serializePublicJson
import io.vertx.core.json.JsonObject

interface Comment : ContentBased {
    val replies: List<Comment>

    override fun asPublicJson(): JsonObject = super
        .asPublicJson()
        .put(CommentJsonKeySpec.replies, replies.serializePublicJson())

    companion object {
        fun createDefault(
            author: User,
            content: HTMLContent,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp = now,
            attachments: List<AttachmentURL> = listOf(),
            replies: List<Comment> = listOf()
        ): Comment = CommentImpl(author, content, createTimeStamp, updateTimestamp, attachments, replies)

        object CommentJsonKeySpec {
            const val replies = "replies"
        }
    }

}
