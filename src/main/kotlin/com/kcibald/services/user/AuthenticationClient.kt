package com.kcibald.services.user

import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory
import io.vertx.kotlin.core.eventbus.requestAwait
import io.vertx.kotlin.core.json.jsonObjectOf
import java.util.concurrent.TimeUnit

const val AUTHENTICATION_ENDPOINT = "kcibald.user.authentication"

class AuthenticationClient(
    private val vertx: Vertx
) {

    private val logger = LoggerFactory.getLogger(AuthenticationClient::class.java)

    suspend fun verifyCredential(email: String, password: String): Boolean {
        logger.debug("verifying user $email though user service")
        val message = vertx
            .eventBus()
            .requestAwait<JsonObject>(
                AUTHENTICATION_ENDPOINT,
                jsonObjectOf(
                    "email" to email,
                    "password" to password
                ),
                DeliveryOptions()
                    .setSendTimeout(TimeUnit.SECONDS.toMillis(2))
            )

        logger.debug("verify user $email response received, ok")
        val body = message.body()
        val validKey = "valid"
        val value = body.getValue(validKey, null)
        if (value != null && value is Boolean)
            return value
        else
            throw AssertionError("response should have an \"valid\" of type boolean")
    }
}