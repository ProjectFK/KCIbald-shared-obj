package com.kcibald.utils

import org.bouncycastle.util.encoders.Hex
import java.util.*

object IDUtil {

    private val base64Encoder = Base64.getUrlEncoder()

    fun encodeDBID(dbId: String): String {
        val bytes = Hex.decode(dbId)
        return base64Encoder.encodeToString(bytes)
    }

    private val base64Decoder = Base64.getUrlDecoder()

    fun decodeDBID(target: String): String? = runCatching {
        val bytes = base64Decoder.decode(target)
        Hex.toHexString(bytes)
    }.getOrNull()
}