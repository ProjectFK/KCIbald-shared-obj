package com.kcibald.objects

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.convertValue
import com.kcibald.objects.impl.UserImpl
import com.kcibald.objects.impl.sharedMapper
import io.vertx.codegen.annotations.Mapper
import io.vertx.core.json.JsonObject
import java.util.function.Function

@JsonDeserialize(`as` = UserImpl::class)
interface User {

    val userName: String
    val urlKey: String
    val avatar: File
    val signature: String

    companion object {
        fun createDefault(
            userName: String,
            urlKey: String,
            avatar: File,
            signature: String
        ): User = UserImpl(
            userName,
            urlKey,
            avatar,
            signature
        )


        @field:Mapper
        @JvmField
        val vertxGenToJson: Function<User, JsonObject> = Function(::toJson)

        fun toJson(it: User) = JsonObject(sharedMapper.convertValue<Map<String, Any>>(it))

        @field:Mapper
        @JvmField
        val vertxGenFromJson: Function<JsonObject, User> = Function(::fromJson)

        fun fromJson(it: JsonObject): User = sharedMapper.convertValue(it.map)

    }

}
