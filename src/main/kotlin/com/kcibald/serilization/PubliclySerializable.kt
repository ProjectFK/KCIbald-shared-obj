package com.kcibald.serilization

import com.kcibald.serilization.json.JsonSerializable
import io.vertx.core.json.JsonObject

interface PubliclySerializable: JsonSerializable {
    fun asPublicJson(): JsonObject

    override fun asJson(): JsonObject = asPublicJson()
}