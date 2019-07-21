package com.kcibald.objects.impl

import com.kcibald.objects.*
import java.time.LocalDateTime
import java.time.ZoneOffset

internal data class CommentImpl(
    override val author: User,
    override val content: String,
    override val createTimeStamp: Timestamp,
    override val updateTimestamp: Timestamp,
    override val attachments: List<AttachmentURL>,
    override val replies: List<Comment>
) : Comment

internal data class PostImpl(
    override val id: String,
    override val title: String,
    override val author: User,
    override val content: String,
    override val createTimeStamp: Timestamp,
    override val updateTimestamp: Timestamp,
    override val attachments: List<AttachmentURL>,
    override val comments: List<Comment>
) : Post

internal data class UserImpl(
    override val userId: String,
    override val userName: String,
    override val urlKey: String,
    override val avatar: AttachmentURL,
    override val signature: String
) : User

internal data class AttachmentURLImpl(override val url: String) : AttachmentURL

internal val now: Timestamp
    get() = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)