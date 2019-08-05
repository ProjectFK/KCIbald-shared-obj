package com.kcibald.interfaces

import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.MultiMap
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.Message
import io.vertx.kotlin.core.json.jsonObjectOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import pbandk.Marshaller
import java.util.*

class EventResults {

    open class TestMessage : Message<Any> {
        override fun replyAddress(): String {
            throw AssertionError()
        }

        override fun isSend(): Boolean {
            throw AssertionError()
        }

        override fun body(): Any {
            throw AssertionError()
        }

        override fun address(): String {
            throw AssertionError()
        }

        override fun fail(failureCode: Int, message: String?) {
            throw AssertionError()
        }

        override fun reply(message: Any?) {
            throw AssertionError()
        }

        override fun <R : Any?> reply(message: Any?, replyHandler: Handler<AsyncResult<Message<R>>>?) {
            throw AssertionError()
        }

        override fun reply(message: Any?, options: DeliveryOptions?) {
            throw AssertionError()
        }

        override fun <R : Any?> reply(
            message: Any?,
            options: DeliveryOptions?,
            replyHandler: Handler<AsyncResult<Message<R>>>?
        ) {
            throw AssertionError()
        }

        override fun headers(): MultiMap {
            throw AssertionError()
        }

    }

    @Test
    fun rawEventResult() {
        val buffer = Buffer.buffer("hi!")
        val target = RawEventResult(buffer)

        val message = object : TestMessage() {
            override fun reply(message: Any?) {
                this.payload = message
            }

            var payload: Any? = null
        }

        target.reply(message)

        assertEquals(buffer, message.payload)
    }

    @Test
    fun emptyEventResult() {
        val message = object : TestMessage() {
            override fun reply(message: Any?) {
                this.payload = message
            }

            var payload: Any? = ""
        }

        EmptyEventResult.reply(message)

        assertNull(message.payload)
    }

    @Test
    fun jsonEventResult() {

        val expected = jsonObjectOf("hi" to "hi")

        val message = object : TestMessage() {
            override fun reply(message: Any?) {
                this.payload = message
            }

            var payload: Any? = ""
        }

        val result = JsonEventResult(expected)

        result.reply(message)

        assertEquals(expected, message.payload)
    }

    @Test
    fun failure() {
        val code = 233
        val expectedMessage = "hiiii"
        val result = FailureEventResult(code, expectedMessage)

        val message = object : TestMessage() {
            override fun fail(failureCode: Int, message: String?) {
                this.code = failureCode
                this.message = message
            }

            var code = 0
            var message: String? = null
        }

        result.reply(message)


        assertEquals(code, message.code)
        assertEquals(expectedMessage, message.message)
    }


    private class TestProtobuf(val byteArray: ByteArray) : pbandk.Message<TestProtobuf> {
        override val protoSize: Int
            get() = byteArray.size

        override fun plus(other: TestProtobuf?): TestProtobuf {
            throw AssertionError()
        }

        override fun protoMarshal(m: Marshaller) {
            throw AssertionError()
        }

        override fun protoMarshal(): ByteArray = byteArray
    }

    @Test
    fun protobufResult() {
        val expected = ByteArray(20)
        Random().nextBytes(expected)

        val result = ProtobufEventResult(TestProtobuf(expected))

        val message = object : TestMessage() {
            override fun reply(message: Any?) {
                payload = message
            }

            var payload: Any? = ""
        }

        result.reply(message)

        assertEquals(expected, message.payload)
    }

    @Test
    fun badRequestEventResult() {
        val target = BadRequestEventResult
        var code = 0
        var messageS = ""
        val message = object : TestMessage() {
            override fun fail(failureCode: Int, message: String?) {
                code = failureCode
                messageS = message!!
            }
        }
        target.reply(message)
        data class FailureResponse(
            val code: Int,
            val message: String
        )
        assertEquals(
            FailureResponse(
                400,
                "Bad Request"
            ),
            FailureResponse(code, messageS)
        )
    }

}