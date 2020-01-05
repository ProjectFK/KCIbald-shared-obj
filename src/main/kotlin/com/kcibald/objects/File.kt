package com.kcibald.objects

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.convertValue
import com.kcibald.objects.impl.FileImpl
import com.kcibald.objects.impl.sharedMapper
import io.vertx.codegen.annotations.Mapper
import io.vertx.core.json.JsonObject
import java.util.function.Function as JFunction

@JsonDeserialize(`as` = FileImpl::class)
interface File {
    val identifier: String
    fun resolveAsURL(): String

    companion object {
        fun withIdentifier(identifier: String): File = FileImpl(identifier)

        @field:Mapper
        @JvmField
        val vertxGenFromJson = JFunction<JsonObject, File> { sharedMapper.convertValue(it.map) }

        @field:Mapper
        @JvmField
        val vertxGenToJson = JFunction<File, JsonObject> {
            JsonObject(sharedMapper.convertValue<Map<String, Any>>(it))
        }
    }
}