package com.kcibald.interfaces

import com.kcibald.utils.i
import com.kcibald.utils.w
import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.logging.LoggerFactory
import io.vertx.kotlin.core.eventbus.unregisterAwait
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class ServiceInterface<MESSAGE_TYPE>(
    protected val vertx: Vertx,
    private val eventbusAddress: String,
    private val unexpectedFailureMessage: EventResult? = null
) {

    protected val logger = LoggerFactory.getLogger(javaClass)
    private var consumer: MessageConsumer<MESSAGE_TYPE>? = null

    suspend fun start() {
        if (consumer != null) return

        logger.i { "Service $javaClass starting, initializing" }
        val initResult = runCatching { starting() }
        if (initResult.isFailure) {
            val exception = initResult.exceptionOrNull()!!
            logger.w(exception) { "Service $javaClass start failed at initialization phase $exception" }
            throw exception
        }

        logger.i { "Service $javaClass binding to address: $eventbusAddress" }

        val consumer = vertx
            .eventBus()
            .consumer<MESSAGE_TYPE>(eventbusAddress)

        consumer.handler {
            GlobalScope.launch(vertx.dispatcher()) {
                try {
                    val result = handle(it)
                    result.reply(it)
                } catch (e: Throwable) {
                    if (unexpectedFailureMessage != null) {
                        unexpectedFailureMessage.reply(it)
                    } else {
                        it.fail(500, "unexpected")
                    }
                }
            }
        }

        this.consumer = consumer

        logger.i { "Service $javaClass binded to address: $eventbusAddress, ready for requests" }
    }

    protected open suspend fun starting() {}
    protected open suspend fun stopping() {}

    abstract suspend fun handle(message: Message<MESSAGE_TYPE>): EventResult

    suspend fun stop() {
        val currentConsumer = consumer
        if (currentConsumer != null) {
            logger.i { "Service $javaClass shutting down, calling stopping method" }
            val stopping = runCatching { stopping() }
            if (stopping.isFailure) {
                val exception = stopping.exceptionOrNull()!!
                logger.w(exception) { "Service $javaClass failed to shut down, exception when calling stopping method" }
                throw exception
            }
            logger.i { "Service $javaClass shutting down, unregistering eventbus" }
            currentConsumer.unregisterAwait()
            consumer = null
        }
    }

}
