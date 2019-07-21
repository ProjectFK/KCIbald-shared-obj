package com.kcibald.objects

import com.kcibald.objects.impl.PostImpl
import com.kcibald.objects.impl.now

interface Post : MinimizedPost {
    val comments: List<Comment>
    val attachments: List<Attachment>

    companion object {
        fun createDefault(
            id: String,
            title: String,
            author: User,
            content: String,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp = now,
            attachments: List<Attachment> = listOf(),
            comments: List<Comment> = listOf(),
            urlKey: String,
            parentRegionKey: String,
            commentSize: Int = comments.size
        ): Post = PostImpl(
            id,
            title,
            author,
            content,
            createTimeStamp,
            updateTimestamp,
            attachments,
            comments,
            urlKey,
            parentRegionKey,
            commentSize
        )
    }

}
