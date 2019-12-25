package com.kcibald.objects

import com.kcibald.objects.impl.AttachmentImpl

interface Attachment {
    val file: File
    val name: String

    companion object {
        fun createDefault(
            file: File,
            name: String
        ): Attachment = AttachmentImpl(
            file,
            name
        )

    }
}
