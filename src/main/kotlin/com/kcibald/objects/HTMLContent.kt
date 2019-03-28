package com.kcibald.objects

import com.kcibald.objects.impl.HTMLContentImpl
import com.kcibald.serilization.string.StringSerializable

interface HTMLContent : StringSerializable {
    companion object {
        fun createDefault(content: String): HTMLContent = HTMLContentImpl(content)
    }
}