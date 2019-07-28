package com.kcibald.services.user

import com.kcibald.services.ServiceClient
import com.kcibald.services.user.impl.AuthenticationClientImpl
import io.vertx.core.Vertx
import io.vertx.core.eventbus.ReplyException
import java.util.concurrent.TimeUnit

interface AuthenticationClient: ServiceClient {
    @Throws(ReplyException::class)
    suspend fun verifyCredential(email: String, password: String): AuthenticationResult

    companion object {
        fun createDefault(
            vertx: Vertx,
            authenticationEndpoint: String = "kcibald.user.authentication",
            timeOutInMilliSecond: Long = TimeUnit.SECONDS.toMillis(2)
        ): AuthenticationClient = AuthenticationClientImpl(
            vertx, authenticationEndpoint, timeOutInMilliSecond
        )
    }
}

