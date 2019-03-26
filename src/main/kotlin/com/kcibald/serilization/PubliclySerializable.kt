package com.kcibald.serilization

import io.vertx.core.json.JsonObject

interface PubliclySerializable {
    fun asPublicJson(): JsonObject
}