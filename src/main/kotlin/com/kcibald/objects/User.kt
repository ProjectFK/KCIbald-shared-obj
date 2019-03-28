package com.kcibald.objects

import com.kcibald.objects.impl.UserImpl
import com.kcibald.serilization.PubliclySerializable
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

interface User : PubliclySerializable {

    val userId: String

    val userName: String

    val avatar: AttachmentURL

    val signature: HTMLContent

    override fun asPublicJson(): JsonObject = json {
        obj(
            UserJsonKeySpec.userId to userId,
            UserJsonKeySpec.userName to userName,
            UserJsonKeySpec.avatar to avatar.asString(),
            UserJsonKeySpec.signature to signature.asString()
        )
    }

    companion object {
        fun createDefault(
            userId: String,
            userName: String,
            avatar: AttachmentURL,
            signature: HTMLContent
        ): User = UserImpl(userId, userName, avatar, signature)
    }

    object UserJsonKeySpec {
        const val userId = "id"
        const val userName = "user_name"
        const val avatar = "avatar"
        const val signature = "signature"
    }

}
