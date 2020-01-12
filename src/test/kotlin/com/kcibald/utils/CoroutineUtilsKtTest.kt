package com.kcibald.utils

import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class CoroutineUtilsKtTest {

    @Test
    fun runInCoroutineFromVertxTest(vertx: Vertx, vertxTestContext: VertxTestContext) {
        Vertx.vertx().runOnContext {
            runInCoroutineFromVertx {
                vertxTestContext.completeNow()
            }
        }
    }

    @Test
    fun runInCoroutineFromVertx_not_in_vertx_context() {
        assertThrows<IllegalStateException> {
            runInCoroutineFromVertx {}
        }
    }

    @Test
    fun runInCoroutineFromVertx_custom_context(vertx: Vertx, vertxTestContext: VertxTestContext) {
//        This is based on the fact that in the same vertx context,
//        the same thread is used, I know this is not the best solution,
//        but this is the best I can comeup with

        val context = vertx.orCreateContext
        val getThreadFuture = Future.future<Thread> { promise ->
            context.runOnContext {
                promise.complete(Thread.currentThread())
            }
        }
        // run on another context in another vertx
        Vertx.vertx().runOnContext {
            getThreadFuture.onComplete {
                if (it.failed())
                    vertxTestContext.failNow(it.cause())
                runInCoroutineFromVertx(context.dispatcher()) {
                    vertxTestContext.verify {
                        assertEquals(it.result(), Thread.currentThread())
                        vertxTestContext.completeNow()
                    }
                }
            }
        }
    }

    @Test
    fun runInCoroutineFromVertxAsync_success(vertx: Vertx, vertxTestContext: VertxTestContext) {
        vertx.runOnContext {
            val result = runInCoroutineFromVertxAsync {
                ""
            }
            runBlocking {
                assertEquals("", result.await())
                vertxTestContext.completeNow()
            }
        }
    }

    @Test
    fun runInCoroutineFromVertxAsync_not_in_vertx_context() {
        assertThrows<IllegalStateException> {
            @Suppress("DeferredResultUnused")
            runInCoroutineFromVertxAsync {}
        }
    }


    @Test
    fun runInCoroutineFromVertxAsync_custom_context(vertx: Vertx, vertxTestContext: VertxTestContext) {
        val context = vertx.orCreateContext
        val getThreadFuture = Future.future<Thread> { promise ->
            context.runOnContext {
                promise.complete(Thread.currentThread())
            }
        }
        Vertx.vertx().runOnContext {
            getThreadFuture.onComplete {
                if (it.failed())
                    vertxTestContext.failNow(it.cause())
                val result = runInCoroutineFromVertxAsync(context.dispatcher()) {
                    it.result() == Thread.currentThread()
                }
                vertxTestContext.verify {
                    runBlocking {
                        assertTrue(result.await(), "not on the same thread")
                    }
                    vertxTestContext.completeNow()
                }
            }
        }
    }
}