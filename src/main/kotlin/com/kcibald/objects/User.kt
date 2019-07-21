package com.kcibald.objects

import com.kcibald.objects.impl.UserImpl
import com.kcibald.serilization.json.JsonSerializable
import com.kcibald.serilization.keyspecs.UserJsonKeySpec
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf

interface User : JsonSerializable {

    val userId: String

    val userName: String

    val urlKey: String

    val avatar: Attachment

    val signature: String

    override fun asJson(): JsonObject =
        jsonObjectOf(
            UserJsonKeySpec.userId to userId,
            UserJsonKeySpec.userName to userName,
            UserJsonKeySpec.urlKey to urlKey,
            UserJsonKeySpec.avatar to avatar,
            UserJsonKeySpec.signature to signature
        )

    companion object {
        fun createDefault(
            userId: String,
            userName: String,
            urlKey: String,
            avatar: Attachment,
            signature: String
        ): User = UserImpl(userId, userName, urlKey, avatar, signature)
    }

}
