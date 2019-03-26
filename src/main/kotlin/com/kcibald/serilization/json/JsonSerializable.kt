package com.kcibald.serilization.json

import io.vertx.core.json.JsonObject

interface JsonSerializable {
    fun asJson(): JsonObject
}
