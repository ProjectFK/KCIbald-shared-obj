package com.kcibald.objects

import com.kcibald.objects.impl.FileImpl

interface File {
    val identifier: String
    fun resolveAsURL(): String

    companion object {
        fun withIdentifier(identifier: String): File = FileImpl(identifier)
    }
}