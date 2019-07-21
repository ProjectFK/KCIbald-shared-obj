package com.kcibald.utils

import io.vertx.core.json.JsonObject
import java.util.*

// Because JsonObject is just an proxy to its map
// Making the map unmodifiable will just do the job
fun JsonObject.immutable(): ImmutableJsonObject = JsonObject(Collections.unmodifiableMap(this.map) as Map<String, Any>)

typealias ImmutableJsonObject = JsonObject
