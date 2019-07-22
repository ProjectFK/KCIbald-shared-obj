package com.kcibald.services.user

import io.vertx.core.Vertx
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.closeAwait
import io.vertx.kotlin.core.json.jsonObjectOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
internal class AuthenticationClientTest {

    @BeforeEach
    fun setUp(vertx: Vertx) {
        _messageConsumer = vertx.eventBus().consumer<JsonObject>("kcibald.user.authentication")
        this._authenticationClient = AuthenticationClient(vertx)
    }

    private var _messageConsumer: MessageConsumer<JsonObject>? = null
    private var _authenticationClient: AuthenticationClient? = null

    @Test
    fun verifyLogin_request(context: VertxTestContext) {
        val trueEmail = "example@example.com"
        val truePassword = "42ymRq7FSg"
        val consumer = _messageConsumer!!
        consumer.handler {
            context.verify {
                val body = it.body()
                assertEquals(body.getString("email", "nop"), trueEmail)
                assertEquals(body.getString("password", "nop"), truePassword)
                context.completeNow()
            }
            it.reply(jsonObjectOf("valid" to true))
        }
        runBlocking {
            _authenticationClient!!.verifyCredential(trueEmail, truePassword)
        }
        assertTrue(context.awaitCompletion(2, TimeUnit.SECONDS))
    }

    @Test
    fun verifyLogin_request_malform() {
        val trueEmail = "example@example.com"
        val truePassword = "42ymRq7FSg"
        val consumer = _messageConsumer!!
        consumer.handler {
            it.reply(jsonObjectOf("evil key key" to true))
        }
        assertThrows<AssertionError> {
            runBlocking {
                _authenticationClient!!.verifyCredential(trueEmail, truePassword)
            }
        }
    }

    @Test
    fun verifyLogin_result_true() {
        val trueEmail = "example@example.com"
        val truePassword = "42ymRq7FSg"
        val consumer = _messageConsumer!!
        consumer.handler {
            it.reply(jsonObjectOf("valid" to true))
        }
        assertTrue(
            runBlocking {
                _authenticationClient!!.verifyCredential(trueEmail, truePassword)
            }
        )
    }

    @Test
    fun verifyLogin_result_false() {
        val trueEmail = "example@example.com"
        val truePassword = "42ymRq7FSg"
        val consumer = _messageConsumer!!
        consumer.handler {
            it.reply(jsonObjectOf("valid" to false))
        }
        assertFalse(
            runBlocking {
                _authenticationClient!!.verifyCredential(trueEmail, truePassword)
            }
        )
    }

    @AfterEach
    fun tearDown(vertx: Vertx) = runBlocking {
        vertx.closeAwait()
    }
}