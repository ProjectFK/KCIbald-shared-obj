package com.kcibald.objects

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.convertValue
import com.kcibald.objects.impl.AttachmentImpl
import com.kcibald.objects.impl.sharedMapper
import io.vertx.codegen.annotations.Mapper
import io.vertx.core.json.JsonObject
import java.util.function.Function as JFunction

@JsonDeserialize(`as` = AttachmentImpl::class)
interface Attachment {
    val file: File
    val name: String

    companion object {
        fun createDefault(
            file: File,
            name: String
        ): Attachment = AttachmentImpl(
            file,
            name
        )

        @field:Mapper
        @JvmField
        val vertxGenToJson = JFunction(::toJson)

        fun toJson(it: Attachment) = JsonObject(sharedMapper.convertValue<Map<String, Any>>(it))

        @field:Mapper
        @JvmField
        val vertxGenFromJson = JFunction(::fromJson)

        fun fromJson(it: JsonObject): Attachment =
            sharedMapper.convertValue(it.map)

    }
}
