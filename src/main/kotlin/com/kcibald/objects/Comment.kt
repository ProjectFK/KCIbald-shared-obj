package com.kcibald.objects

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.convertValue
import com.kcibald.objects.impl.CommentImpl
import com.kcibald.objects.impl.now
import com.kcibald.objects.impl.sharedMapper
import io.vertx.codegen.annotations.Mapper
import io.vertx.core.json.JsonObject
import java.util.function.Function

@JsonDeserialize(`as` = CommentImpl::class)
interface Comment : ContentBased {
    val id: Int
    val replies: List<Comment>
    val attachments: List<Attachment>

    companion object {
        fun createDefault(
            id: Int,
            author: User,
            content: String,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp? = null,
            attachments: List<Attachment> = listOf(),
            replies: List<Comment> = listOf()
        ): Comment = CommentImpl(
            id,
            author,
            content,
            createTimeStamp,
            updateTimestamp,
            attachments,
            replies
        )

        @field:Mapper
        @JvmField
        val vertxGenToJson = Function(::toJson)

        fun toJson(it: Comment) = JsonObject(sharedMapper.convertValue<Map<String, Any>>(it))

        @field:Mapper
        @JvmField
        val vertxGenFromJson = Function(::fromJson)

        fun fromJson(it: JsonObject): Comment = sharedMapper.convertValue(it.map)

    }

}