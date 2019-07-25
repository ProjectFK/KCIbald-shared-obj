package com.kcibald.services.user

import com.kcibald.objects.User
import com.kcibald.services.user.proto.AuthenticationRequest
import com.kcibald.services.user.proto.AuthenticationResponse
import com.kcibald.services.user.proto.AuthenticationResponse.Result
import com.kcibald.utils.d
import com.kcibald.utils.w
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.ReplyException
import io.vertx.core.logging.LoggerFactory
import io.vertx.kotlin.core.eventbus.requestAwait
import java.util.concurrent.TimeUnit

class AuthenticationClient(
    private val vertx: Vertx,
    private val authenticationEndpoint: String = "kcibald.user.authentication",
    private val timeOutInMilliSecond: Long = TimeUnit.SECONDS.toMillis(2)
) {

    private val logger = LoggerFactory.getLogger(AuthenticationClient::class.java)

    @Throws(ReplyException::class)
    suspend fun verifyCredential(email: String, password: String): AuthenticationResult {
        logger.d { "verifying user $email though user service" }
        val message = try {
            vertx
                .eventBus()
                .requestAwait<ByteArray>(
                    authenticationEndpoint,
                    AuthenticationRequest(email, password).protoMarshal(),
                    DeliveryOptions()
                        .setSendTimeout(timeOutInMilliSecond)
                )
        } catch (e: ReplyException) {
            throw e
        } catch (e: Exception) {
            val message = "exception when requesting authentication verification though event bus for user $email"
            logger.warn(message, e)
            return AuthenticationResult.SystemError(message)
        }

        logger.d { "verify user $email response received, ok" }
        val body = AuthenticationResponse.protoUnmarshal(message.body())
        return when (val result = body.result) {
            is Result.SuccessUser -> {
                val user = result.successUser
                AuthenticationResult.Success(
                    User.createDefault(
                        user.userId,
                        user.userName,
                        user.urlKey,
                        user.avatarKey,
                        user.signature
                    )
                )
            }
            is Result.CommonAuthenticationError -> {
                when (result.commonAuthenticationError) {
                    AuthenticationResponse.AuthenticationErrorType.USER_NOT_FOUND
                    -> AuthenticationResult.UserNotFound
                    AuthenticationResponse.AuthenticationErrorType.INVALID_CREDENTIAL
                    -> AuthenticationResult.InvalidCredential
                    else ->
                        throw AssertionError("should not have another type of common authentication error")
                }
            }
            is Result.BannedInfo -> {
                val bannedInfo = result.bannedInfo
                AuthenticationResult.Banned(
                    bannedInfo.timeBanned,
                    bannedInfo.duration,
                    bannedInfo.message
                )
            }
            is Result.SystemErrorMessage -> {
                logger.w { "error received from " }
                AuthenticationResult.SystemError(result.systemErrorMessage)
            }
            else ->
                throw AssertionError("should not have another authentication response type")
        }
    }
}

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