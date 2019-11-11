package com.kcibald.services.files.impl

import com.kcibald.objects.File
import com.kcibald.services.files.ImageFileResolverClient

object ImageFileResolverClientImpl : ImageFileResolverClient {
    override suspend fun translateImageTokenToURL(file: File): String? {
        if (!file.startsWith('v')) return null
        val versionSplitPoint = file.indexOf(':')
        if (versionSplitPoint == -1) return null
        val versionString = file.substring(1, versionSplitPoint)
        val versionInt = versionString.toIntOrNull() ?: return null
        return transferToVersion(versionInt, file.substring(versionSplitPoint + 1))
    }

    private fun transferToVersion(versionInt: Int, token: String): String? {
        return when (versionInt) {
            1 -> versionOne(token)
            else -> null
        }
    }

    private fun versionOne(token: String): String? {
        val domainSplitPoint = token.indexOf('-')
        if (domainSplitPoint == -1)
            return null
        val domainNum = token.substring(1, domainSplitPoint).toIntOrNull()
        val subToken = token.substring(domainSplitPoint + 1)
        val domain = when {
//            should not hard code this, load this from config or somesort
            domainNum == 0 -> "cdn.kcibald.com"
            else -> null
        } ?: return null
        return "$domain/v1-$subToken"
    }

}