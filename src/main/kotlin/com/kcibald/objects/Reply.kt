package com.kcibald.objects

import com.kcibald.objects.impl.ReplyImpl
import com.kcibald.objects.impl.now

interface Reply : ContentBased {
    companion object {
        fun createDefault(
            author: User,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp = now,
            content: HTMLContent,
            attachments: List<AttachmentURL> = listOf()
        ): Reply = ReplyImpl(author, createTimeStamp, updateTimestamp, content, attachments)
    }
}