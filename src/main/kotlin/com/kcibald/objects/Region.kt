package com.kcibald.objects

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.convertValue
import com.kcibald.objects.impl.RegionImpl
import com.kcibald.objects.impl.sharedMapper
import com.kcibald.utils.PageableCollection
import com.kcibald.utils.toURLKey
import io.vertx.codegen.annotations.Mapper
import io.vertx.core.json.JsonObject
import java.util.function.Function as JFunction

@JsonDeserialize(`as` = RegionImpl::class)
interface Region {
    val name: String
    val urlKey: String
    val parent: Region?
    val description: String
    val avatar: File
    val topPosts: PageableCollection<MinimizedPost>
    val childRegion: List<Region>
    val colors: Colors

    data class Colors(
        val left: String,
        val right: String
    )

    companion object {
        fun createDefault(
            name: String,
            description: String,
            avatar: File,
            colors: Colors,
            urlKey: String = name.toURLKey(),
            parent: Region? = null,
            topPosts: List<MinimizedPost> = emptyList(),
            childRegion: List<Region> = emptyList()
        ): Region = RegionImpl(
            name,
            urlKey,
            parent,
            description,
            avatar,
            PageableCollection.directCollection(topPosts),
            childRegion,
            colors
        )

        @field:Mapper
        @JvmField
        val vertxGenToJson: JFunction<Region, JsonObject> = JFunction(::toJson)

        fun toJson(it: Region) = JsonObject(sharedMapper.convertValue<Map<String, Any>>(it))

        @field:Mapper
        @JvmField
        val vertxGenFromJson: JFunction<JsonObject, Region> = JFunction(::fromJson)

        fun fromJson(it: JsonObject): Region = sharedMapper.convertValue(it.map)

    }
}