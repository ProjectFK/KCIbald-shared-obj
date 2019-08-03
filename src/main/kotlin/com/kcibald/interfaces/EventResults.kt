package com.kcibald.interfaces

import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject


interface EventResult {
    fun reply(message: Message<*>)
}

class RawEventResult(
    private val buffer: Buffer
) : EventResult {
    override fun reply(message: Message<*>) = message.reply(buffer)
}

object EmptyEventResult : EventResult {
    override fun reply(message: Message<*>) {
        message.reply(null)
    }
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
