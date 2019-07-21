package com.kcibald.serilization

import com.kcibald.serilization.json.JsonSerializable
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonArrayOf
import io.vertx.kotlin.core.json.jsonObjectOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SerializationHelpersKtTest {

    @Test
    fun serializeCollection() {
        val jobj1 = jsonObjectOf(
            "a1" to "1",
            "a2" to "2"
        )
        val jobj2 = jsonObjectOf(
            "b1" to "1",
            "b2" to "2"
        )
        val jArray = jsonArrayOf(jobj1, jobj2)

        val obj1 = object : JsonSerializable {
            override fun asJson(): JsonObject = jobj1
        }
        val obj2 = object : JsonSerializable {
            override fun asJson(): JsonObject = jobj2
        }

        assertEquals(jArray, listOf(obj1, obj2).serializeToJson())
    }

}