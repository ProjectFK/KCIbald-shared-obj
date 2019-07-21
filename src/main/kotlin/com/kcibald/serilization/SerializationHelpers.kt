package com.kcibald.serilization

import com.kcibald.serilization.json.JsonSerializable
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import java.util.function.BiConsumer
import java.util.function.Supplier

fun Collection<JsonSerializable>.serializeToJson(): JsonArray = stream()
    .map(JsonSerializable::asJson)
    .collect(
        Supplier(::JsonArray),
        BiConsumer { t: JsonArray, u: JsonObject -> t.add(u) },
        emptyBiConsumer
    )

internal val emptyBiConsumer: BiConsumer<JsonArray, JsonArray> = BiConsumer { _, _ -> }