package com.kcibald.interfaces

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pbandk.Marshaller
import pbandk.Message

internal class EventResultsKtTest {

    object TestMessage : Message<TestMessage> {
        override fun protoMarshal(m: Marshaller) {
            throw AssertionError()
        }

        override fun plus(other: TestMessage?): TestMessage = this

        override val protoSize: Int
            get() = 0

    }

    @Test
    fun toEventResult() {
        val proto = TestMessage
        assertEquals(ProtobufEventResult(proto), proto.toEventResult())
    }
}