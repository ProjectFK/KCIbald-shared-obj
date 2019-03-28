package com.kcibald.objects

import com.kcibald.objects.impl.PostImpl
import com.kcibald.objects.impl.now
import com.kcibald.serilization.serializePublicJson
import io.vertx.core.json.JsonObject

interface Post : ContentBased {
    val id: String
    val title: String
    val comments: List<Comment>
    override fun asPublicJson(): JsonObject = super
        .asPublicJson()
        .put(PostJsonKeySpec.comments, comments.serializePublicJson())
        .put(PostJsonKeySpec.id, id)

    companion object {
        fun createDefault(
            id: String,
            title: String,
            author: User,
            content: HTMLContent,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp = now,
            attachments: List<AttachmentURL> = listOf(),
            comments: List<Comment> = listOf()
        ): Post = PostImpl(id, title, author, content, createTimeStamp, updateTimestamp, attachments, comments)
    }

    object PostJsonKeySpec {
        const val comments = "comments"
        const val id = "post_id"
    }
}
