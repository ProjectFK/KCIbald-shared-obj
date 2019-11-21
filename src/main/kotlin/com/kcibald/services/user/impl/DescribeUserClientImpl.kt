package com.kcibald.services.user.impl

import com.kcibald.interfaces.SubsetIdentifiable
import com.kcibald.objects.User
import com.kcibald.services.Result
import com.kcibald.services.user.DescribeUserClient
import com.kcibald.services.user.proto.DescribeUserRequest
import com.kcibald.services.user.proto.DescribeUserRequest.By
import com.kcibald.services.user.proto.DescribeUserResponse
import com.kcibald.utils.d
import com.kcibald.utils.w
import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.ReplyException
import io.vertx.core.logging.LoggerFactory
import io.vertx.kotlin.core.eventbus.requestAwait

class DescribeUserClientImpl(
    private val vertx: Vertx,
    private val eventBusAddress: String
) : DescribeUserClient {

    private val logger = LoggerFactory.getLogger(DescribeUserClientImpl::class.java)

    override suspend fun describeUser(urlKey: String): Result<User> =
        describeUserInternal(By.UrlKey(urlKey))

    override suspend fun describeThoughIdentifier(identifier: SubsetIdentifiable<User>): Result<User> =
        describeUserInternal(By.ID(identifier.sid))

    private suspend fun describeUserInternal(by: By): Result<User> {
        logger.d { "sending request to describe user by $by" }
        val message = performRequestNullIfFail(by)

        if (message == null)
            return Result.failure("exception when performing request")

        return parseAndProcessMessage(message)
    }

    private fun parseAndProcessMessage(message: Message<ByteArray>): Result<User> {
        return when (val result = parseResultFromMessage(message)) {
            is DescribeUserResponse.Result.UserNotFound ->
                Result.notFound()
            is DescribeUserResponse.Result.SystemErrorMessage ->
                Result.failure(result.systemErrorMessage)
            is DescribeUserResponse.Result.SingleUserResult ->
                Result.success(translateDBUser(result.singleUserResult.result!!))
            else ->
                throw AssertionError("should not reach here")
        }
    }

    private fun translateDBUser(dbUser: com.kcibald.services.user.proto.User): User =
        User.createDefault(
            dbUser.userName,
            dbUser.urlKey,
            dbUser.avatarKey,
            dbUser.signature
        )

    private fun parseResultFromMessage(message: Message<ByteArray>) =
        DescribeUserResponse.protoUnmarshal(message.body()).result

    private suspend fun performRequestNullIfFail(by: By): Message<ByteArray>? {
        try {
            return sendRequest(by)
        } catch (e: ReplyException) {
            logger.w {
                "reply exception when trying to send request to describe user by $by, e: $e"
            }
            throw e
        } catch (e: Exception) {
            logger.w(e) {
                "unexpected exception when performing request though event bus ($eventBusAddress), " +
                        "attempting to describe user by $by, exception received: $e"
            }
            return null
        }
    }

    private suspend fun sendRequest(by: By): Message<ByteArray> =
        vertx
            .eventBus()
            .requestAwait(
                eventBusAddress,
                DescribeUserRequest(by).protoMarshal()
            )

    override val clientVersion: String = "PREVIEW"

    override val compatibleServiceVersion: String = "UNPUBLISHED"
}