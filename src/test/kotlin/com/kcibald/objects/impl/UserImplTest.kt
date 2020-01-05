package com.kcibald.objects.impl

import com.kcibald.objects.File
import com.kcibald.objects.User
import com.kcibald.objects.invoke
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserImplTest {

    val signature = "signature"
    val avatar = File.withIdentifier("url")
    val userId = "user_id"
    val userName = "name"
    val urlKey = "name"
    val user = UserImpl(userName, urlKey, avatar, signature)

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
        assertEquals(user, UserImpl(userName, urlKey, avatar, signature))
    }

    @Test
    fun json() {
        assertEquals(user, User.vertxGenFromJson(User.vertxGenToJson(user)))
    }

}