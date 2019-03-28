package com.kcibald.serilization

import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.JsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PubliclySerializableTest {

    val jObject = JsonObject("a" to "b")
    val target = object : PubliclySerializable {
        override fun asPublicJson(): JsonObject = jObject
    }

    @Test
    fun defaultAsJson() {
        assertEquals(jObject, target.asJson())
    }

    @Test
    fun defaultAsJsonPointer() {
        assert(target.asPublicJson() === target.asJson())
    }
    
}