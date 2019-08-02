package com.kcibald.interfaces

import com.kcibald.utils.i
import com.kcibald.utils.w
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory
import io.vertx.kotlin.core.eventbus.unregisterAwait
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

abstract class ServiceInterface<MESSAGE_TYPE>(
    protected val vertx: Vertx,
    protected val eventbusAddress: String,
    private val unexpectedFailureMessage: EventResult? = null
) {

    protected val logger = LoggerFactory.getLogger(javaClass)
    protected val consumer: MessageConsumer<*>? = null

    @Synchronized
    suspend fun start() {
        if (consumer != null) return

        logger.i { "Service $javaClass starting, initializing" }
        val initResult = runCatching { initialize() }
        if (initResult.isFailure) {
            val exception = initResult.exceptionOrNull()!!
            logger.w(exception) { "Service $javaClass start failed at initialization phase $exception" }
            throw exception
        }

        logger.i { "Service $javaClass binding to address: $eventbusAddress" }

        val consumer = vertx
            .eventBus()
            .consumer<MESSAGE_TYPE>(eventbusAddress)

        consumer.coroutineHandler<MESSAGE_TYPE>(
            vertx,
            unexpectedFailureMessage = unexpectedFailureMessage,
            block = ::handle
        )

        logger.i { "Service $javaClass binded to address: $eventbusAddress, ready for requests" }
    }

    open suspend fun initialize() {}

    abstract suspend fun handle(message: Message<MESSAGE_TYPE>): EventResult

    @Synchronized
    suspend fun stop() {
        if (consumer != null) {
            consumer.unregisterAwait()
        }
    }

}

interface EventResult {
    fun reply(message: Message<*>)
}

class RawEventResult(
    private val buffer: Buffer
) : EventResult {
    override fun reply(message: Message<*>) = message.reply(buffer)
}

object EmptyEventResult : EventResult {
    override fun reply(message: Message<*>) {}
}

class FailureEventResult(
    private val statusCode: Int,
    private val failureMessage: String
) : EventResult {
    override fun reply(message: Message<*>) = message.fail(
        statusCode,
        failureMessage
    )
}

class JsonEventResult(
    private val jsonObject: JsonObject
) : EventResult {
    override fun reply(message: Message<*>) {
        message.reply(jsonObject)
    }
}

class ProtobufEventResult<T : pbandk.Message<T>>(
    val message: pbandk.Message<T>
) : EventResult {
    override fun reply(message: Message<*>) {
        val payload = this.message.protoMarshal()
        message.reply(payload)
    }

}

internal fun <IN> MessageConsumer<IN>.coroutineHandler(
    vertx: Vertx,
    unexpectedFailureMessage: EventResult? = null,
    block: suspend (Message<IN>) -> EventResult
) {
    handler {
        GlobalScope.launch(vertx.dispatcher()) {
            try {
                val result = block(it)
                result.reply(it)
            } catch (e: Exception) {
                if (unexpectedFailureMessage != null) {
                    unexpectedFailureMessage.reply(it)
                } else {
                    it.fail(500, "unexpected")
                }
            }
        }
    }
}
