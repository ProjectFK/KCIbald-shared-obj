package com.kcibald.objects.impl

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

}