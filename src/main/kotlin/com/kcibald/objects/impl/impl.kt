package com.kcibald.objects.impl

import com.kcibald.objects.*
import com.kcibald.services.files.ImageFileResolverClient
import com.kcibald.utils.KnownSizePageableCollection
import com.kcibald.utils.PageableCollection
import java.time.LocalDateTime
import java.time.ZoneOffset

internal data class CommentImpl(
    override val id: Int,
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
    override val comments: KnownSizePageableCollection<Comment>,
    override val urlKey: String,
    override val sourceRegionURLKey: String,
    override val commentCount: Int
) : Post

internal data class UserImpl(
    override val userName: String,
    override val urlKey: String,
    override val avatar: File,
    override val signature: String
) : User

internal data class RegionImpl(
    override val name: String,
    override val urlKey: String,
    override val parent: Region?,
    override val description: String,
    override val avatar: File,
    override val topPosts: PageableCollection<MinimizedPost>,
    override val childRegion: List<Region>,
    override val colors: Region.Colors
) : Region

internal data class AttachmentImpl(
    override val file: File,
    override val name: String
) : Attachment

internal data class FileImpl(
    override val identifier: String
) : File {
    override fun resolveAsURL(): String =
        ImageFileResolverClient
            .getInstance()
            .translateImageTokenToURL(identifier)
            ?: throw IllegalStateException("identifier invalid upon convertion")
}

internal val now: Timestamp
    get() = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)