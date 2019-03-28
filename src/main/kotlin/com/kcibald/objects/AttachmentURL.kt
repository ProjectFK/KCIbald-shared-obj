package com.kcibald.objects

import com.kcibald.objects.impl.AttachmentURLImpl
import com.kcibald.serilization.string.StringSerializable

interface AttachmentURL: StringSerializable {

    val url: String

    override fun asString(): String = url

    companion object {
        fun createDefault(url: String): AttachmentURL = AttachmentURLImpl(url)
    }

}
