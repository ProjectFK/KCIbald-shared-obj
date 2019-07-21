package com.kcibald.objects

import com.kcibald.objects.impl.CommentImpl
import com.kcibald.objects.impl.now

interface Comment : ContentBased {
    val id: Int
    val replies: List<Comment>
    val attachments: List<Attachment>

    companion object {
        fun createDefault(
            id: Int,
            author: User,
            content: String,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp? = null,
            attachments: List<Attachment> = listOf(),
            replies: List<Comment> = listOf()
        ): Comment = CommentImpl(
            id,
            author,
            content,
            createTimeStamp,
            updateTimestamp,
            attachments,
            replies
        )

    }

}
