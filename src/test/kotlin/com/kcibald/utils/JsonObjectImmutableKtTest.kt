package com.kcibald.utils

import io.vertx.core.json.JsonObject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class JsonObjectImmutableKtTest {

    @Test
    fun jsonObject() {
        val json = JsonObject()
        json.put("a", "b")
        val immutableJson = json.immutable()
        assertThrows<UnsupportedOperationException> {
            immutableJson.put("c", "d")
        }
    }

}