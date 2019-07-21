package com.kcibald.objects

import com.kcibald.objects.impl.UserImpl

interface User {

    val userId: String

    val userName: String

    val urlKey: String

    val avatar: Attachment

    val signature: String

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
