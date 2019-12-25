package com.kcibald.objects

import com.kcibald.objects.impl.UserImpl

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

    }

}
