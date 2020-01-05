package com.kcibald.utils

import com.kcibald.objects.MinimizedPost
import com.kcibald.objects.Post

object ObjUtils {
    fun minimize(post: Post): MinimizedPost =
        MinimizedPost.createDefault(
            post.title,
            post.content,
            post.author,
            post.commentCount,
            post.sourceRegionURLKey,
            post.urlKey,
            post.createTimestamp,
            post.updateTimestamp
        )
}