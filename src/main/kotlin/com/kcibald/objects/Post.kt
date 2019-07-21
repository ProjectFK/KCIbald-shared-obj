package com.kcibald.objects

import com.kcibald.objects.impl.PostImpl
import com.kcibald.objects.impl.now
import com.kcibald.serilization.keyspecs.PostJsonKeySpec
import com.kcibald.serilization.serializeToJson
import io.vertx.core.json.JsonObject

interface Post : MinimizedPost {
    val comments: List<Comment>
    val attachments: List<Attachment>

    override fun asJson(): JsonObject  = super
        .asJson()
        .put(PostJsonKeySpec.comments, comments.serializeToJson())
        .put(PostJsonKeySpec.attachments, attachments)

    companion object {
        fun createDefault(
            id: String,
            title: String,
            author: User,
            content: String,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp = now,
            attachments: List<Attachment> = listOf(),
            comments: List<Comment> = listOf()
        ): Post = PostImpl(id, title, author, content, createTimeStamp, updateTimestamp, attachments, comments)
    }

}
