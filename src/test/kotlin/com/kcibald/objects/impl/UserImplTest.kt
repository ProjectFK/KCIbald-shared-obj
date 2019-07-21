package com.kcibald.objects.impl

import com.kcibald.objects.AttachmentURL
import com.kcibald.objects.User
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserImplTest {

    val signature = "signature"
    val avatar = AttachmentURL.createDefault("url")
    val userId = "user_id"
    val userName = "name"
    val user = UserImpl(userId, userName, avatar, signature)

    @Test
    fun getUserId() {
        assertEquals(userId, user.userId)
    }

    @Test
    fun getUserName() {
        assertEquals(userName, user.userName)
    }

    @Test
    fun getAvatar() {
        assertEquals(avatar, user.avatar)
    }

    @Test
    fun getSignature() {
        assertEquals(signature, user.signature)
    }

    @Test
    fun equals() {
        assertEquals(user, UserImpl(userId, userName, avatar, signature))
    }

    @Test
    fun asJson() {
        val json = json {
            obj(
                User.UserJsonKeySpec.signature to signature,
                User.UserJsonKeySpec.avatar to avatar.asString(),
                User.UserJsonKeySpec.userName to userName,
                User.UserJsonKeySpec.userId to userId
            )
        }
        assertEquals(json, user.asJson())
    }

}