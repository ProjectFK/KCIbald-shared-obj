package com.kcibald.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.Marker

internal class LoggerUtilsKtTest {

    open class DelegateLogger : Logger {
        override fun warn(message: String): Unit = throw IllegalAccessError()
        override fun warn(format: String, arg: Any): Unit = throw IllegalAccessError()
        override fun warn(message: String, vararg params: Any): Unit = throw IllegalAccessError()
        override fun warn(format: String, arg1: Any, arg2: Any): Unit = throw IllegalAccessError()
        override fun warn(message: String, t: Throwable): Unit = throw IllegalAccessError()
        override fun warn(marker: Marker, msg: String): Unit = throw IllegalAccessError()
        override fun warn(marker: Marker, format: String, arg: Any): Unit = throw IllegalAccessError()
        override fun warn(marker: Marker, format: String, arg1: Any, arg2: Any): Unit = throw IllegalAccessError()
        override fun warn(marker: Marker, format: String, vararg arguments: Any): Unit = throw IllegalAccessError()
        override fun warn(marker: Marker, msg: String, t: Throwable): Unit = throw IllegalAccessError()
        override fun getName(): String = throw IllegalAccessError()
        override fun info(message: String): Unit = throw IllegalAccessError()
        override fun info(format: String, arg: Any): Unit = throw IllegalAccessError()
        override fun info(format: String, arg1: Any, arg2: Any): Unit = throw IllegalAccessError()
        override fun info(message: String, vararg params: Any) = throw IllegalAccessError()
        override fun info(message: String, t: Throwable): Unit = throw IllegalAccessError()
        override fun info(marker: Marker, msg: String): Unit = throw IllegalAccessError()
        override fun info(marker: Marker, format: String, arg: Any): Unit = throw IllegalAccessError()
        override fun info(marker: Marker, format: String, arg1: Any, arg2: Any): Unit = throw IllegalAccessError()
        override fun info(marker: Marker, format: String, vararg arguments: Any): Unit = throw IllegalAccessError()
        override fun info(marker: Marker, msg: String, t: Throwable): Unit = throw IllegalAccessError()
        override fun isErrorEnabled(): Boolean = throw IllegalAccessError()
        override fun isErrorEnabled(marker: Marker): Boolean = throw IllegalAccessError()
        override fun error(message: String) = throw IllegalAccessError()
        override fun error(format: String, arg: Any): Unit = throw IllegalAccessError()
        override fun error(format: String, arg1: Any, arg2: Any): Unit = throw IllegalAccessError()
        override fun error(message: String, vararg params: Any) = throw IllegalAccessError()
        override fun error(message: String, t: Throwable) = throw IllegalAccessError()
        override fun error(marker: Marker, msg: String): Unit = throw IllegalAccessError()
        override fun error(marker: Marker, format: String, arg: Any): Unit = throw IllegalAccessError()
        override fun error(marker: Marker, format: String, arg1: Any, arg2: Any): Unit = throw IllegalAccessError()
        override fun error(marker: Marker, format: String, vararg arguments: Any): Unit = throw IllegalAccessError()
        override fun error(marker: Marker, msg: String, t: Throwable): Unit = throw IllegalAccessError()
        override fun isDebugEnabled(): Boolean = throw IllegalAccessError()
        override fun isDebugEnabled(marker: Marker): Boolean = throw IllegalAccessError()
        override fun debug(message: String): Unit = throw IllegalAccessError()
        override fun debug(format: String, arg: Any): Unit = throw IllegalAccessError()
        override fun debug(format: String, arg1: Any, arg2: Any): Unit = throw IllegalAccessError()
        override fun debug(message: String, vararg params: Any) = throw IllegalAccessError()
        override fun debug(message: String, t: Throwable): Unit = throw IllegalAccessError()
        override fun debug(marker: Marker, msg: String): Unit = throw IllegalAccessError()
        override fun debug(marker: Marker, format: String, arg: Any): Unit = throw IllegalAccessError()
        override fun debug(marker: Marker, format: String, arg1: Any, arg2: Any): Unit = throw IllegalAccessError()
        override fun debug(marker: Marker, format: String, vararg arguments: Any): Unit = throw IllegalAccessError()
        override fun debug(marker: Marker, msg: String, t: Throwable): Unit = throw IllegalAccessError()
        override fun isInfoEnabled(): Boolean = throw IllegalAccessError()
        override fun isInfoEnabled(marker: Marker): Boolean = throw IllegalAccessError()
        override fun isWarnEnabled(): Boolean = throw IllegalAccessError()
        override fun isWarnEnabled(marker: Marker): Boolean = throw IllegalAccessError()
        override fun trace(message: String): Unit = throw IllegalAccessError()
        override fun trace(format: String, arg: Any): Unit = throw IllegalAccessError()
        override fun trace(format: String, arg1: Any, arg2: Any): Unit = throw IllegalAccessError()
        override fun trace(message: String, vararg params: Any) = throw IllegalAccessError()
        override fun trace(message: String, t: Throwable): Unit = throw IllegalAccessError()
        override fun trace(marker: Marker, msg: String): Unit = throw IllegalAccessError()
        override fun trace(marker: Marker, format: String, arg: Any): Unit = throw IllegalAccessError()
        override fun trace(marker: Marker, format: String, arg1: Any, arg2: Any): Unit = throw IllegalAccessError()
        override fun trace(marker: Marker, format: String, vararg argArray: Any): Unit = throw IllegalAccessError()
        override fun trace(marker: Marker, msg: String, t: Throwable): Unit = throw IllegalAccessError()
        override fun isTraceEnabled(): Boolean = throw IllegalAccessError()
        override fun isTraceEnabled(marker: Marker): Boolean = throw IllegalAccessError()
    }

    @Test
    fun trace_enable() {
        val expect = ":D"
        var received: Any? = null
        val target = object : DelegateLogger() {
            override fun isTraceEnabled() = true
            override fun trace(message: String) {
                received = message
            }
        }
        target.t {
            expect
        }
        assertEquals(expect, received)
    }

    @Test
    fun trace_enable_throw() {
        val expect = ":D"
        val exception = Exception()

        var received: Any? = null
        var receivedE: Throwable? = null

        val target = object : DelegateLogger() {
            override fun isTraceEnabled(): Boolean = true
            override fun trace(message: String) {
                fail<Unit>()
            }

            override fun trace(message: String, t: Throwable) {
                received = message
                receivedE = t
            }
        }
        target.t(exception) {
            expect
        }
        assertEquals(expect, received)
        assertEquals(exception, receivedE)
    }

    @Test
    fun trace_disable() {
        val expect = ":("
        var received: Any? = null
        val target = object : DelegateLogger() {
            override fun isTraceEnabled(): Boolean = false
            override fun trace(message: String) {
                received = message
            }
        }
        target.t {
            expect
        }
        assertNull(received)
    }

    @Test
    fun debug_enable() {
        val expect = ":D"
        var received: Any? = null
        val target = object : DelegateLogger() {
            override fun isDebugEnabled(): Boolean = true
            override fun debug(message: String) {
                received = message
            }
        }
        target.d {
            expect
        }
        assertEquals(expect, received)
    }

    @Test
    fun debug_enable_throw() {
        val expect = ":D"
        val exception = Exception()

        var received: Any? = null
        var receivedE: Throwable? = null

        val target = object : DelegateLogger() {
            override fun isDebugEnabled(): Boolean = true
            override fun debug(message: String) {
                fail<Unit>()
            }

            override fun debug(message: String, t: Throwable) {
                received = message
                receivedE = t
            }
        }
        target.d(exception) {
            expect
        }
        assertEquals(expect, received)
        assertEquals(exception, receivedE)
    }

    @Test
    fun debug_disable() {
        val expect = ":("
        var received: Any? = null
        val target = object : DelegateLogger() {
            override fun isDebugEnabled(): Boolean = false
            override fun debug(message: String) {
                received = message
            }
        }
        target.d {
            expect
        }
        assertNull(received)
    }

    @Test
    fun info_enable() {
        val expect = ":D"
        var received: Any? = null
        val target = object : DelegateLogger() {
            override fun isInfoEnabled(): Boolean = true
            override fun info(message: String) {
                received = message
            }
        }
        target.i {
            expect
        }
        assertEquals(expect, received)
    }

    @Test
    fun info_enable_throw() {
        val expect = ":D"
        val exception = Exception()

        var received: Any? = null
        var receivedE: Throwable? = null

        val target = object : DelegateLogger() {
            override fun isInfoEnabled(): Boolean = true
            override fun info(message: String) {
                fail<Unit>()
            }

            override fun info(message: String, t: Throwable) {
                received = message
                receivedE = t
            }
        }
        target.i(exception) {
            expect
        }
        assertEquals(expect, received)
        assertEquals(exception, receivedE)
    }

    @Test
    fun info_disable() {
        val expect = ":("
        var received: Any? = null
        val target = object : DelegateLogger() {
            override fun isInfoEnabled(): Boolean = false
            override fun info(message: String) {
                received = message
            }
        }
        target.i {
            expect
        }
        assertNull(received)
    }

    @Test
    fun warn_enable() {
        val expect = ":D"
        var received: Any? = null
        val target = object : DelegateLogger() {
            override fun isWarnEnabled(): Boolean = true
            override fun warn(message: String) {
                received = message
            }
        }
        target.w {
            expect
        }
        assertEquals(expect, received)
    }

    @Test
    fun warn_enable_throw() {
        val expect = ":D"
        val exception = Exception()

        var received: Any? = null
        var receivedE: Throwable? = null

        val target = object : DelegateLogger() {
            override fun isWarnEnabled(): Boolean = true
            override fun warn(message: String) {
                fail<Unit>()
            }

            override fun warn(message: String, t: Throwable) {
                received = message
                receivedE = t
            }
        }
        target.w(exception) {
            expect
        }
        assertEquals(expect, received)
        assertEquals(exception, receivedE)
    }

    @Test
    fun warn_disable() {
        val expect = ":("
        var received: Any? = null
        val target = object : DelegateLogger() {
            override fun isWarnEnabled(): Boolean = false
            override fun warn(message: String) {
                received = message
            }
        }
        target.w {
            expect
        }
        assertNull(received)
    }
}