package com.kcibald.interfaces

import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.ReplyException
import io.vertx.junit5.VertxExtension
import io.vertx.kotlin.core.eventbus.requestAwait
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class ServiceInterfaceTest {

    @Test
    fun start(vertx: Vertx) {
        var tripped = false

        val target = object : ServiceInterface<Unit>(vertx, "default") {
            override suspend fun starting() {
                tripped = true
            }

            override suspend fun handle(message: Message<Unit>): EventResult {
                fail<Unit>()
                throw AssertionError()
            }
        }

        runBlocking {
            target.start()
        }

        assertTrue(tripped, "start method should call starting")

        tripped = false
        runBlocking {
            target.start()
        }

        assertFalse(tripped, "calling start method when the service is started should not trigger starting")
    }

    @Test
    fun start_bad(vertx: Vertx) {
        val target = object : ServiceInterface<Unit>(vertx, "default") {
            override suspend fun handle(message: Message<Unit>): EventResult {
                throw AssertionError()
            }

            override suspend fun starting() {
                throw RuntimeException(":(")
            }

        }

        assertThrows(RuntimeException::class.java) {
            runBlocking { target.start() }
        }
    }

    @Test
    fun stop(vertx: Vertx) {
        var tripped = false

        val target = object : ServiceInterface<Unit>(vertx, "default") {
            override suspend fun handle(message: Message<Unit>): EventResult {
                fail<Unit>()
                throw AssertionError()
            }

            override suspend fun stopping() {
                tripped = true
            }
        }

        runBlocking {
            target.start()
            target.stop()
        }
        assertTrue(tripped, "stop method should call stopping")

        tripped = false
        runBlocking {
            target.stop()
        }

        assertFalse(tripped, "calling start method when the service is stopped should not trigger stopping")
    }

    @Test
    fun stop_bad(vertx: Vertx) {
        val target = object : ServiceInterface<Unit>(vertx, "default") {
            override suspend fun handle(message: Message<Unit>): EventResult {
                throw AssertionError()
            }

            override suspend fun stopping() {
                throw RuntimeException(":(")
            }

        }

        runBlocking {
            target.start()
        }

        assertThrows(RuntimeException::class.java) {
            runBlocking { target.stop() }
        }
    }

    @Test
    fun functional(vertx: Vertx) {
        val eventbusAddress = "bus"
        var result: Message<String>? = null
        val target = object : ServiceInterface<String>(vertx, eventbusAddress) {
            override suspend fun handle(message: Message<String>): EventResult {
                result = message
                return EmptyEventResult
            }
        }

        val message = "hi"

        runBlocking {
            target.start()
            vertx.eventBus().requestAwait<String>(eventbusAddress, message)
        }

        assertEquals(message, result!!.body())
    }

    @Test
    fun exception_handle_default(vertx: Vertx) {
        val eventbusAddress = "bus"
        val target = object : ServiceInterface<String>(vertx, eventbusAddress) {
            override suspend fun handle(message: Message<String>): EventResult {
                throw RuntimeException(":(")
            }
        }

        val message = "hi"

        runBlocking { target.start() }

        val exception = assertThrows(ReplyException::class.java) {
            runBlocking { vertx.eventBus().requestAwait<String>(eventbusAddress, message) }
        }

        assertEquals(500, exception.failureCode())
        assertEquals("unexpected", exception.message)

        Unit
    }

    @Test
    fun exception_handle(vertx: Vertx) {
        val eventbusAddress = "bus"
        val expected = EmptyEventResult

        val target = object : ServiceInterface<String>(vertx, eventbusAddress, unexpectedFailureMessage = expected) {
            override suspend fun handle(message: Message<String>): EventResult {
                throw RuntimeException(":(")
            }
        }

        runBlocking { target.start() }

        val m = runBlocking { vertx.eventBus().requestAwait<String>(eventbusAddress, "hi") }
        assertNull(m.body())

        Unit
    }

    @Test
    fun vertx(vertx: Vertx) {
        val target = object : ServiceInterface<Unit>(vertx, "default") {
            override suspend fun handle(message: Message<Unit>): EventResult {
                throw AssertionError()
            }

            fun exposeVertx(): Vertx {
                return this.vertx
            }
        }

        assertEquals(vertx, target.exposeVertx())
    }


}