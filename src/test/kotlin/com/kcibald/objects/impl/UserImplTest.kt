package com.kcibald.objects.impl

import com.kcibald.serilization.keyspecs.UserJsonKeySpec
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserImplTest {

    val signature = "signature"
    val avatar = "url"
    val userId = "user_id"
    val userName = "name"
    val urlKey = "name"
    val user = UserImpl(userId, userName, urlKey, avatar, signature)

    @Test
    fun getUserId() {
        assertEquals(userId, user.userId)
    }

    @Test
    fun getUserName() {
        assertEquals(userName, user.userName)
    }

    @Test
    fun getUrlKey() {
        assertEquals(urlKey, user.urlKey)
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
        assertEquals(user, UserImpl(userId, userName, urlKey, avatar, signature))
    }

    @Test
    fun asJson() {
        val json = json {
            obj(
                UserJsonKeySpec.signature to signature,
                UserJsonKeySpec.avatar to avatar,
                UserJsonKeySpec.userName to userName,
                UserJsonKeySpec.urlKey to urlKey,
                UserJsonKeySpec.userId to userId
            )
        }
        assertEquals(json, user.asJson())
    }

}