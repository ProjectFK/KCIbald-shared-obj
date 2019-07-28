package com.kcibald.services.user

import com.kcibald.objects.User

sealed class AuthenticationResult {
    data class Success(
        val user: User
    ) : AuthenticationResult()

    object UserNotFound : AuthenticationResult()
    object InvalidCredential : AuthenticationResult()

    data class SystemError(
        val message: String
    ) : AuthenticationResult()

    data class Banned(
        val timeBanned: Int,
        val duration: Int,
        val message: String
    ) : AuthenticationResult()
}