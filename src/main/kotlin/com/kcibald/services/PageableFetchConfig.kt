package com.kcibald.services

import com.fasterxml.jackson.module.kotlin.convertValue
import com.kcibald.objects.impl.sharedMapper
import io.vertx.codegen.annotations.Mapper
import io.vertx.core.json.JsonObject
import java.util.function.Function

data class PageableFetchConfig(
    val count: Int = DEFAULT_POST_PER_PAGE,
    val skip: Int = 0,
    val queryMark: String? = null
) {
    companion object {
        const val DEFAULT_POST_PER_PAGE: Int = 20

        @field:Mapper
        @JvmField
        val vertxGenToJson: Function<PageableFetchConfig, JsonObject> = Function {
            JsonObject(sharedMapper.convertValue<Map<String, Any>>(it))
        }

        @field:Mapper
        @JvmField
        val vertxGenFromJson: Function<JsonObject, PageableFetchConfig> = Function {
            sharedMapper.convertValue(it.map)
        }
    }
}

val defaultPageableFetchConfig = PageableFetchConfig()