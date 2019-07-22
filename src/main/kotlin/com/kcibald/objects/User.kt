package com.kcibald.objects

import com.kcibald.objects.impl.UserImpl

interface User {

    val userId: String
    val userName: String
    val urlKey: String
    val avatar: File
    val signature: String

    companion object {
        fun createDefault(
            userId: String,
            userName: String,
            urlKey: String,
            avatar: File,
            signature: String
        ): User = UserImpl(
            userId,
            userName,
            urlKey,
            avatar,
            signature
        )
    }

}
