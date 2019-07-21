package com.kcibald.objects.impl

import com.kcibald.objects.*
import com.kcibald.utils.DirectCollection
import com.kcibald.utils.KnownSizePageableCollection
import com.kcibald.utils.PageableCollection
import java.time.LocalDateTime
import java.time.ZoneOffset

internal data class CommentImpl(
    override val author: User,
    override val content: String,
    override val createTimestamp: Timestamp,
    override val updateTimestamp: Timestamp?,
    override val attachments: List<Attachment>,
    override val replies: List<Comment>
) : Comment

internal data class MinimizedPostImpl(
    override val title: String,
    override val urlKey: String,
    override val sourceRegionURLKey: String,
    override val commentCount: Int,
    override val author: User,
    override val createTimestamp: Timestamp,
    override val updateTimestamp: Timestamp?,
    override val content: String
) : MinimizedPost

internal data class PostImpl(
    override val title: String,
    override val author: User,
    override val content: String,
    override val createTimestamp: Timestamp,
    override val updateTimestamp: Timestamp?,
    override val attachments: List<Attachment>,
    val comments_collection: List<Comment>,
    override val urlKey: String,
    override val sourceRegionURLKey: String,
    override val commentCount: Int
) : Post {
    override val comments: KnownSizePageableCollection<Comment>

    init {
        comments = DirectCollection(comments_collection)
    }
}

internal data class UserImpl(
    override val userId: String,
    override val userName: String,
    override val urlKey: String,
    override val avatar: Attachment,
    override val signature: String
) : User

internal data class RegionImpl(
    override val name: String,
    override val urlKey: String,
    override val parent: Region?,
    override val description: String,
    override val avatar: Attachment,
    val topPosts_collection: List<Post>,
    override val childRegion: List<Region>
) : Region {
    override val topPosts: PageableCollection<Post>

    init {
        topPosts = DirectCollection(topPosts_collection)
    }

}

internal val now: Timestamp
    get() = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)