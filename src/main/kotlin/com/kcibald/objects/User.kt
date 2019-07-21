package com.kcibald.objects

import com.kcibald.objects.impl.UserImpl
import com.kcibald.serilization.json.JsonSerializable
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf

interface User : JsonSerializable {

    val userId: String

    val userName: String

    val avatar: AttachmentURL

    val signature: String

    override fun asJson(): JsonObject =
        jsonObjectOf(
            UserJsonKeySpec.userId to userId,
            UserJsonKeySpec.userName to userName,
            UserJsonKeySpec.avatar to avatar.asString(),
            UserJsonKeySpec.signature to signature
        )

    companion object {
        fun createDefault(
            userId: String,
            userName: String,
            avatar: AttachmentURL,
            signature: String
        ): User = UserImpl(userId, userName, avatar, signature)
    }

    object UserJsonKeySpec {
        const val userId = "user_id"
        const val userName = "user_name"
        const val avatar = "avatar"
        const val signature = "signature"
    }

}
