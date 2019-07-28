package com.kcibald.services.user.impl

import com.kcibald.interfaces.SubsetIdentifiable
import com.kcibald.objects.User
import com.kcibald.services.Result
import com.kcibald.services.user.DescribeUserClient
import com.kcibald.services.user.proto.DescribeUserRequest
import com.kcibald.services.user.proto.DescribeUserResponse
import com.kcibald.services.user.proto.Empty
import io.vertx.core.Vertx
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.eventbus.ReplyException
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
internal class DescribeUserClientImplTest {

    lateinit var target: DescribeUserClientImpl
    lateinit var eventBus: MessageConsumer<ByteArray>

    @BeforeEach
    fun setUp(vertx: Vertx) {
        target = DescribeUserClientImpl(vertx, DescribeUserClient.DEFAULT_EVENT_BUS_ADDRESS)
        eventBus = vertx.eventBus().consumer(DescribeUserClient.DEFAULT_EVENT_BUS_ADDRESS)
    }

    @Test
    fun describeUser_request(context: VertxTestContext) {
        val expected = "urlKey"

        eventBus.handler {
            val (by, _) = DescribeUserRequest.protoUnmarshal(it.body())
            context.verify {
                assert(by is DescribeUserRequest.By.UrlKey)
                assertEquals(expected, (by as DescribeUserRequest.By.UrlKey).urlKey)
                context.completeNow()
            }
            it.fail(500, "no result for you!")
        }
        assertThrows<ReplyException> {
            runBlocking {
                target.describeUser(expected)
            }
        }

        context.awaitCompletion(2, TimeUnit.SECONDS)
        Unit
    }

    @Test
    fun describeUser_request_customized_eventbus_addr(vertx: Vertx, context: VertxTestContext) {
        val expected = "urlKey"

        val newAddress = "new_address"

        target = DescribeUserClientImpl(
            vertx,
            newAddress
        )
        eventBus = vertx.eventBus().consumer(newAddress)

        eventBus.handler {
            val (by, _) = DescribeUserRequest.protoUnmarshal(it.body())
            context.verify {
                assert(by is DescribeUserRequest.By.UrlKey)
                assertEquals(expected, (by as DescribeUserRequest.By.UrlKey).urlKey)
                context.completeNow()
            }
            it.fail(500, "no result for you!")
        }
        assertThrows<ReplyException> {
            runBlocking {
                target.describeUser(expected)
            }
        }

        context.awaitCompletion(2, TimeUnit.SECONDS)
        Unit
    }


    @Test
    fun describeUser_not_found() {
        eventBus.handler {
            val response = DescribeUserResponse(
                DescribeUserResponse.Result.UserNotFound(Empty())
            )
            it.reply(response.protoMarshal())
        }

        val result = runBlocking {
            target.describeUser("urlKey")
        }

        assert(result is Result.NotFound)
    }

    @Test
    fun describeUser_sys_error() {
        val expectedMessage = "message"
        eventBus.handler {
            val response = DescribeUserResponse(
                DescribeUserResponse.Result.SystemErrorMessage(expectedMessage)
            )
            it.reply(response.protoMarshal())
        }

        val result = runBlocking {
            target.describeUser("urlKey")
        }

        assert(result is Result.Failure)
        assertEquals(expectedMessage, (result as Result.Failure).message)
    }

    @Test
    fun describeUser_success() {
        val userName = ""
        val urlKey = ""
        val signature = ""
        val avatarKey = ""

        eventBus.handler {
            val response = DescribeUserResponse(
                DescribeUserResponse.Result.SingleUserResult(
                    DescribeUserResponse.SuccessSingleUserResult(
                        com.kcibald.services.user.proto.User(
                            userName = userName,
                            urlKey = urlKey,
                            signature = signature,
                            avatarKey = avatarKey
                        )
                    )
                )
            )
            it.reply(response.protoMarshal())
        }

        val result = runBlocking {
            target.describeUser(urlKey)
        }

        assert(result is Result.Success)
        assertEquals(
            User.createDefault(userName, urlKey, avatarKey, signature),
            (result as Result.Success).result
        )
    }

    @Test
    fun describeThoughIdentifier_request(context: VertxTestContext) {
        val id = object : SubsetIdentifiable<User>{
            override val sid: String
                get() = ""
        }

        eventBus.handler {
            val (by, _) = DescribeUserRequest.protoUnmarshal(it.body())
            context.verify {
                assert(by is DescribeUserRequest.By.ID)
                assertEquals(id.sid, (by as DescribeUserRequest.By.ID).iD)
                context.completeNow()
            }
            it.fail(500, "no result for you!")
        }

        assertThrows<ReplyException> {
            runBlocking {
                target.describeThoughIdentifier(id)
            }
        }

        context.awaitCompletion(2, TimeUnit.SECONDS)
        Unit
    }
    @Test
    fun describeThoughIdentifier_request_customized_eventbus_addr(vertx: Vertx, context: VertxTestContext) {
        val id = object : SubsetIdentifiable<User>{
            override val sid: String
                get() = ""
        }

        val newAddr = "new_address"

        target = DescribeUserClientImpl(vertx, newAddr)
        eventBus = vertx.eventBus().consumer(newAddr)

        eventBus.handler {
            val (by, _) = DescribeUserRequest.protoUnmarshal(it.body())
            context.verify {
                assert(by is DescribeUserRequest.By.ID)
                assertEquals(id.sid, (by as DescribeUserRequest.By.ID).iD)
                context.completeNow()
            }
            it.fail(500, "no result for you!")
        }

        assertThrows<ReplyException> {
            runBlocking {
                target.describeThoughIdentifier(id)
            }
        }

        context.awaitCompletion(2, TimeUnit.SECONDS)
        Unit
    }



    @Test
    fun describeThoughIdentifier_not_found() {
        eventBus.handler {
            val response = DescribeUserResponse(
                DescribeUserResponse.Result.UserNotFound(Empty())
            )
            it.reply(response.protoMarshal())
        }

        val result = runBlocking {
            target.describeThoughIdentifier(object : SubsetIdentifiable<User> {
                override val sid: String
                    get() = "sid"
            })
        }

        assert(result is Result.NotFound)
    }


    @Test
    fun describeThoughIdentifier_sys_error() {
        val expectedMessage = "message"
        eventBus.handler {
            val response = DescribeUserResponse(
                DescribeUserResponse.Result.SystemErrorMessage(expectedMessage)
            )
            it.reply(response.protoMarshal())
        }

        val result = runBlocking {
            target.describeThoughIdentifier(object : SubsetIdentifiable<User> {
                override val sid: String
                    get() = "idididid"
            })
        }

        assert(result is Result.Failure)
        assertEquals(expectedMessage, (result as Result.Failure).message)
    }


    @Test
    fun describeThoughIdentifier_success() {
        val userName = ""
        val urlKey = ""
        val signature = ""
        val avatarKey = ""

        eventBus.handler {
            val response = DescribeUserResponse(
                DescribeUserResponse.Result.SingleUserResult(
                    DescribeUserResponse.SuccessSingleUserResult(
                        com.kcibald.services.user.proto.User(
                            userName = userName,
                            urlKey = urlKey,
                            signature = signature,
                            avatarKey = avatarKey
                        )
                    )
                )
            )
            it.reply(response.protoMarshal())
        }

        val result = runBlocking {
            target.describeThoughIdentifier(object : SubsetIdentifiable<User> {
                override val sid: String
                    get() = "idididid"
            })
        }

        assert(result is Result.Success)
        assertEquals(
            User.createDefault(userName, urlKey, avatarKey, signature),
            (result as Result.Success).result
        )
    }

    @Test
    fun getClientVersion() {
        assertEquals("PREVIEW", target.clientVersion)
    }

    @Test
    fun getCompatibleServiceVersion() {
        assertEquals("UNPUBLISHED", target.compatibleServiceVersion)
    }
}