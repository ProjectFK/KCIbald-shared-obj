package com.kcibald.objects

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.convertValue
import com.kcibald.objects.impl.PostImpl
import com.kcibald.objects.impl.now
import com.kcibald.objects.impl.sharedMapper
import com.kcibald.utils.PageableCollection
import com.kcibald.utils.toURLKey
import io.vertx.codegen.annotations.Mapper
import io.vertx.core.json.JsonObject
import java.util.function.Function as JFunction

@JsonDeserialize(`as` = PostImpl::class)
interface Post : MinimizedPost {
    val comments: PageableCollection<Comment>
    val attachments: List<Attachment>

    companion object {
        fun createDefault(
            title: String,
            author: User,
            content: String,
            parentRegionKey: String,
            createTimeStamp: Timestamp = now,
            updateTimestamp: Timestamp? = null,
            attachments: List<Attachment> = listOf(),
            comments: List<Comment> = listOf(),
            urlKey: String = title.toURLKey()
        ): Post = PostImpl(
            title,
            author,
            content,
            createTimeStamp,
            updateTimestamp,
            attachments,
            PageableCollection.directCollection(comments),
            urlKey,
            parentRegionKey,
            comments.size
        )

        @field:Mapper
        @JvmField
        val vertxGenToJson: JFunction<Post, JsonObject> = JFunction {
            JsonObject(sharedMapper.convertValue<Map<String, Any>>(it))
        }

        @field:Mapper
        @JvmField
        val vertxGenFromJson: JFunction<JsonObject, Post> = JFunction {
            sharedMapper.convertValue(it.map)
        }

    }

}
