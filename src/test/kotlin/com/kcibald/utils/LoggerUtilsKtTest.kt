package com.kcibald.utils

import io.vertx.core.logging.Logger
import io.vertx.core.spi.logging.LogDelegate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class LoggerUtilsKtTest {

    open class DelegateWarrper: LogDelegate {
        override fun warn(message: Any?): Unit = throw IllegalAccessError()

        override fun warn(message: Any?, vararg params: Any?) = throw IllegalAccessError()

        override fun warn(message: Any?, t: Throwable?) = throw IllegalAccessError()

        override fun warn(message: Any?, t: Throwable?, vararg params: Any?) = throw IllegalAccessError()

        override fun fatal(message: Any?) = throw IllegalAccessError()

        override fun fatal(message: Any?, t: Throwable?) = throw IllegalAccessError()

        override fun info(message: Any?): Unit = throw IllegalAccessError()

        override fun info(message: Any?, vararg params: Any?) = throw IllegalAccessError()

        override fun info(message: Any?, t: Throwable?) = throw IllegalAccessError()

        override fun info(message: Any?, t: Throwable?, vararg params: Any?) = throw IllegalAccessError()

        override fun error(message: Any?) = throw IllegalAccessError()

        override fun error(message: Any?, vararg params: Any?) = throw IllegalAccessError()

        override fun error(message: Any?, t: Throwable?) = throw IllegalAccessError()

        override fun error(message: Any?, t: Throwable?, vararg params: Any?) = throw IllegalAccessError()

        override fun isDebugEnabled(): Boolean = throw IllegalAccessError()

        override fun debug(message: Any?): Unit = throw IllegalAccessError()

        override fun debug(message: Any?, vararg params: Any?) = throw IllegalAccessError()

        override fun debug(message: Any?, t: Throwable?) = throw IllegalAccessError()

        override fun debug(message: Any?, t: Throwable?, vararg params: Any?) = throw IllegalAccessError()

        override fun isInfoEnabled(): Boolean = throw IllegalAccessError()

        override fun isWarnEnabled(): Boolean = throw IllegalAccessError()

        override fun trace(message: Any?) = throw IllegalAccessError()

        override fun trace(message: Any?, vararg params: Any?) = throw IllegalAccessError()

        override fun trace(message: Any?, t: Throwable?) = throw IllegalAccessError()

        override fun trace(message: Any?, t: Throwable?, vararg params: Any?) = throw IllegalAccessError()

        override fun isTraceEnabled(): Boolean = throw IllegalAccessError()
    }

    @Test
    fun debug_enable() {
        val expect = ":D"
        var received: Any? = null
        val target = Logger(object : DelegateWarrper() {
            override fun isDebugEnabled(): Boolean = true
            override fun debug(message: Any?) {
                received = message
            }
        })
        target.d {
            expect
        }
        assertEquals(expect, received)
    }

    @Test
    fun debug_disable() {
        val expect = ":("
        var received: Any? = null
        val target = Logger(object : DelegateWarrper() {
            override fun isDebugEnabled(): Boolean = false
            override fun debug(message: Any?) {
                received = message
            }
        })
        target.d {
            expect
        }
        assertNull(received)
    }

    @Test
    fun info_enable() {
        val expect = ":D"
        var received: Any? = null
        val target = Logger(object : DelegateWarrper() {
            override fun isInfoEnabled(): Boolean = true
            override fun info(message: Any?) {
                received = message
            }
        })
        target.i {
            expect
        }
        assertEquals(expect, received)
    }

    @Test
    fun info_disable() {
        val expect = ":("
        var received: Any? = null
        val target = Logger(object : DelegateWarrper() {
            override fun isInfoEnabled(): Boolean = false
            override fun info(message: Any?) {
                received = message
            }
        })
        target.i {
            expect
        }
        assertNull(received)
    }

    @Test
    fun warn_enable() {
        val expect = ":D"
        var received: Any? = null
        val target = Logger(object : DelegateWarrper() {
            override fun isWarnEnabled(): Boolean = true
            override fun warn(message: Any?) {
                received = message
            }
        })
        target.w {
            expect
        }
        assertEquals(expect, received)
    }

    @Test
    fun warn_disable() {
        val expect = ":("
        var received: Any? = null
        val target = Logger(object : DelegateWarrper() {
            override fun isWarnEnabled(): Boolean = false
            override fun warn(message: Any?) {
                received = message
            }
        })
        target.w {
            expect
        }
        assertNull(received)
    }
}