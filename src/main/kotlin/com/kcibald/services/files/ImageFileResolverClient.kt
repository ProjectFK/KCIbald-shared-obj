package com.kcibald.services.files

import com.kcibald.objects.File

interface ImageFileResolverClient {
    suspend fun translateImageTokenToURL(file: File): String?
}