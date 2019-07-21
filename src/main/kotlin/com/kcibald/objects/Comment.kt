package com.kcibald.objects

import com.kcibald.objects.impl.CommentImpl
import com.kcibald.objects.impl.now
import com.kcibald.serilization.keyspecs.CommentJsonKeySpec
import com.kcibald.serilization.serializeToJson
import io.vertx.core.json.JsonObject

interface Comment : ContentBased {
    val replies: List<Comment>

    override fun asJson(): JsonObject  = super
        .asJson()
        .put(CommentJsonKeySpec.replies, replies.serializeToJson())

    companion object {
        fun createDefault(
            author: User,
            content: String,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp = now,
            attachments: List<Attachment> = listOf(),
            replies: List<Comment> = listOf()
        ): Comment = CommentImpl(author, content, createTimeStamp, updateTimestamp, attachments, replies)

    }

}
