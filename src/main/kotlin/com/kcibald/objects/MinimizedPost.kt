package com.kcibald.objects

import com.kcibald.objects.impl.MinimizedPostImpl
import com.kcibald.objects.impl.now

interface MinimizedPost : ContentBased {
    val id: String
    val title: String
    val urlKey: String
    val parentRegionUrlKey: String
    val commentCount: Int

    companion object {
        fun createDefault(
            id: String,
            title: String,
            urlKey: String,
            content: String,
            author: User,
            parentRegionUrlKey: String,
            commentCount: Int,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp? = null
        ): MinimizedPost = MinimizedPostImpl(
            id,
            title,
            urlKey,
            parentRegionUrlKey,
            commentCount,
            author,
            createTimeStamp,
            updateTimestamp,
            content
        )
    }

}