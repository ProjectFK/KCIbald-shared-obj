package com.kcibald.services.files

import com.kcibald.services.files.impl.ImageFileResolverClientImpl

interface ImageFileResolverClient {
    fun translateImageTokenToURL(token: String): String?

    companion object {
        fun getInstance(): ImageFileResolverClient = ImageFileResolverClientImpl
    }
}