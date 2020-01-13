package com.kcibald.objects

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.convertValue
import com.kcibald.objects.impl.MinimizedPostImpl
import com.kcibald.objects.impl.now
import com.kcibald.objects.impl.sharedMapper
import com.kcibald.utils.toURLKey
import io.vertx.codegen.annotations.Mapper
import io.vertx.core.json.JsonObject
import java.util.function.Function as JFunction

@JsonDeserialize(`as` = MinimizedPostImpl::class)
interface MinimizedPost : ContentBased {
    val title: String
    val urlKey: String
    val sourceRegionURLKey: String
    val commentCount: Int

    companion object {
        fun createDefault(
            title: String,
            content: String,
            author: User,
            commentCount: Int,
            parentRegionUrlKey: String,
            urlKey: String = title.toURLKey(),
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp? = null
        ): MinimizedPost = MinimizedPostImpl(
            title,
            urlKey,
            parentRegionUrlKey,
            commentCount,
            author,
            createTimeStamp,
            updateTimestamp,
            content
        )

        @field:Mapper
        @JvmField
        val vertxGenToJson: JFunction<MinimizedPost, JsonObject> = JFunction(::toJson)

        fun toJson(it: MinimizedPost) = JsonObject(sharedMapper.convertValue<Map<String, Any>>(it))

        @field:Mapper
        @JvmField
        val vertxGenFromJson: JFunction<JsonObject, MinimizedPost> = JFunction(::fromJson)

        fun fromJson(it: JsonObject): MinimizedPost = sharedMapper.convertValue(it.map)

    }

}