package com.kcibald.services.user

import com.kcibald.objects.File
import com.kcibald.objects.User.Companion.createDefault
import com.kcibald.services.user.impl.AuthenticationClientImpl
import com.kcibald.services.user.proto.AuthenticationRequest
import com.kcibald.services.user.proto.AuthenticationResponse
import io.vertx.core.Vertx
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.eventbus.ReplyException
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.closeAwait
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Duration
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
internal class AuthenticationClientImplTest {

    @BeforeEach
    fun setUp(vertx: Vertx) {
        consumer = vertx.eventBus().consumer("kcibald.user.authentication")
        this.authenticationClient = AuthenticationClient.createDefault(vertx)
    }

    private lateinit var consumer: MessageConsumer<ByteArray>
    private var authenticationClient: AuthenticationClient? = null

    @Test
    fun verifyLogin_request(context: VertxTestContext) {
        val trueEmail = "example@example.com"
        val truePassword = "42ymRq7FSg"
        consumer.handler {
            context.verify {
                val request = AuthenticationRequest.protoUnmarshal(it.body())
                assertEquals(request.userEmail, trueEmail)
                assertEquals(request.plainPassword, truePassword)
                context.completeNow()
            }
            it.fail(500, "no result for you!")
        }
        assertThrows(ReplyException::class.java) {
            runBlocking {
                authenticationClient!!.verifyCredential(trueEmail, truePassword)
            }
        }
        assertTrue(context.awaitCompletion(2, TimeUnit.SECONDS))
    }

    @Test
    fun verifyLogin_reply_failure() {
        consumer.handler {
            it.fail(500, "no result for you!")
        }
        assertThrows(ReplyException::class.java) {
            fireRequest()
        }
    }

    @Test
    fun verifyLogin_unexpected_failure() {
        val errorMessage = "error!"
        consumer.handler {
            val response = AuthenticationResponse(
                AuthenticationResponse.Result.SystemErrorMessage(errorMessage)
            )
            it.reply(response.protoMarshal())
        }
        val result = fireRequest()

        result as AuthenticationResult.SystemError

        assertEquals(errorMessage, result.message)
    }

    @Test
    fun verifyLogin_success() {
        val userId = "user_id"
        val userName = "user_name"
        val urlKey = "url_key"
        val signature = "signature"
        val avatarKey = "avatarKey"

        val expected = createDefault(
        userName,
            urlKey,
            File.withIdentifier(avatarKey), signature
    )

        consumer.handler {
            val response = AuthenticationResponse(
                AuthenticationResponse.Result.SuccessUser(
                    com.kcibald.services.user.proto.User(
                        userId,
                        userName,
                        urlKey,
                        signature,
                        avatarKey

                    )
                )
            )
            it.reply(response.protoMarshal())
        }

        val result = fireRequest()
        assert(result is AuthenticationResult.Success) { "result should have a type of Success" }
        result as AuthenticationResult.Success

        assertEquals(expected, result.user)
    }

    @Test
    fun verifyLogin_common_failure_incorrectPassword() {
        consumer.handler {
            val response = AuthenticationResponse(
                AuthenticationResponse.Result.CommonAuthenticationError(
                    AuthenticationResponse.AuthenticationErrorType.INVALID_CREDENTIAL
                )
            )
            it.reply(response.protoMarshal())
        }

        val result = fireRequest()

        assertEquals(AuthenticationResult.InvalidCredential, result)
    }


    @Test
    fun verifyLogin_common_failure_not_found() {
        consumer.handler {
            val response = AuthenticationResponse(
                AuthenticationResponse.Result.CommonAuthenticationError(
                    AuthenticationResponse.AuthenticationErrorType.USER_NOT_FOUND
                )
            )
            it.reply(response.protoMarshal())
        }

        val result = fireRequest()

        assertEquals(AuthenticationResult.UserNotFound, result)
    }

    @Test
    fun verifyLogin_sys_fail() {
        val message = "message"
        consumer.handler {
            val response = AuthenticationResponse(
                AuthenticationResponse.Result.SystemErrorMessage(message)
            )
            it.reply(response.protoMarshal())
        }

        val result = fireRequest()

        assert(result is AuthenticationResult.SystemError) { "result should have a type of SystemError" }

        result as AuthenticationResult.SystemError

        assertEquals(message, result.message)
    }

    @Test
    fun verifyLogin_banned() {
        val timeBanned = 12345
        val duration = 2124
        val message = "u suck"

        consumer.handler {
            val response = AuthenticationResponse(
                AuthenticationResponse.Result.BannedInfo(
                    AuthenticationResponse.BannedInfo(
                        timeBanned,
                        duration,
                        message
                    )
                )
            )
            it.reply(response.protoMarshal())
        }

        val result = fireRequest()

        assert(result is AuthenticationResult.Banned) { "result should have a type of Banned" }

        result as AuthenticationResult.Banned

        assertEquals(timeBanned, result.timeBanned)
        assertEquals(duration, result.duration)
        assertEquals(message, result.message)
    }

    @Test
    fun config_endpoint(vertx: Vertx, context: VertxTestContext) {
        val endpoint = "test.kcibald.com"
        this.authenticationClient =
            AuthenticationClient.createDefault(vertx, authenticationEndpoint = "test.kcibald.com")
        consumer = vertx.eventBus().consumer(endpoint)
        consumer.handler {
            context.completeNow()
            it.fail(500, "no result for you!")
        }
        assertThrows(ReplyException::class.java) {
            fireRequest()
        }
        context.awaitCompletion(2, TimeUnit.SECONDS)
    }

    @Test
    fun config_timeout(vertx: Vertx) {
        this.authenticationClient = AuthenticationClient.createDefault(
            vertx,
            timeOutInMilliSecond = 1
        )
        consumer.handler { message ->
            vertx.setTimer(TimeUnit.SECONDS.toMillis(3)) {
                message.fail(500, "no result for you!")
            }
        }
        assertTimeout(Duration.ofSeconds(1)) {
            assertThrows(ReplyException::class.java) {
                fireRequest()
            }
        }
    }

    @Test
    fun verifyLogin_unexpected(vertx: Vertx) = runBlocking{
        val target = AuthenticationClientImpl(
            vertx,
            "kcibald.user.authentication",
            -1
        )
        val result = target.verifyCredential("", "")

        assert(result is AuthenticationResult.SystemError)
        result as AuthenticationResult.SystemError
        assertEquals(
            "exception when requesting authentication verification though event bus for user ",
            result.message
        )
    }

    @Test
    fun clientVersion() {
        assertEquals("alpha-1.0.0-preview", authenticationClient!!.clientVersion)
    }

    @Test
    fun compatibleServiceVersion() {
        assertEquals("alpha-1.0.0-preview", authenticationClient!!.compatibleServiceVersion)
    }


    private fun fireRequest() = runBlocking {
        authenticationClient!!.verifyCredential("email", "sureSecurep@ssw0rd!")
    }

    @AfterEach
    fun tearDown(vertx: Vertx) = runBlocking {
        vertx.closeAwait()
    }
}