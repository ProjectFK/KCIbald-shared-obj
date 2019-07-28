package com.kcibald.objects

import com.kcibald.objects.impl.PostImpl
import com.kcibald.objects.impl.now
import com.kcibald.utils.DirectCollection
import com.kcibald.utils.KnownSizePageableCollection
import com.kcibald.utils.toURLKey

interface Post : MinimizedPost {
    val comments: KnownSizePageableCollection<Comment>
    val attachments: List<Attachment>

    companion object {
        fun createDefault(
            title: String,
            author: User,
            content: String,
            parentRegionKey: String,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp? = null,
            attachments: List<Attachment> = listOf(),
            comments: List<Comment> = listOf(),
            urlKey: String = title.toURLKey()
        ): Post = PostImpl(
            title,
            author,
            content,
            createTimeStamp,
            updateTimestamp,
            attachments,
            DirectCollection(comments),
            urlKey,
            parentRegionKey,
            comments.size
        )
    }

}
