@file:Suppress("DEPRECATION")

package com.kcibald.utils

import io.vertx.core.logging.Logger
import io.vertx.core.spi.logging.LogDelegate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LoggerUtilsLegacyKtTest {

    open class DelegateWarrper : LogDelegate {
        override fun warn(message: Any?): Unit = throw IllegalAccessError()

        override fun warn(message: Any?, vararg params: Any?) = throw IllegalAccessError()

        override fun warn(message: Any?, t: Throwable?): Unit = throw IllegalAccessError()

        override fun warn(message: Any?, t: Throwable?, vararg params: Any?) = throw IllegalAccessError()

        override fun fatal(message: Any?) = throw IllegalAccessError()

        override fun fatal(message: Any?, t: Throwable?) = throw IllegalAccessError()

        override fun info(message: Any?): Unit = throw IllegalAccessError()

        override fun info(message: Any?, vararg params: Any?) = throw IllegalAccessError()

        override fun info(message: Any?, t: Throwable?): Unit = throw IllegalAccessError()

        override fun info(message: Any?, t: Throwable?, vararg params: Any?) = throw IllegalAccessError()

        override fun error(message: Any?) = throw IllegalAccessError()

        override fun error(message: Any?, vararg params: Any?) = throw IllegalAccessError()

        override fun error(message: Any?, t: Throwable?) = throw IllegalAccessError()

        override fun error(message: Any?, t: Throwable?, vararg params: Any?) = throw IllegalAccessError()

        override fun isDebugEnabled(): Boolean = throw IllegalAccessError()

        override fun debug(message: Any?): Unit = throw IllegalAccessError()

        override fun debug(message: Any?, vararg params: Any?) = throw IllegalAccessError()

        override fun debug(message: Any?, t: Throwable?): Unit = throw IllegalAccessError()

        override fun debug(message: Any?, t: Throwable?, vararg params: Any?) = throw IllegalAccessError()

        override fun isInfoEnabled(): Boolean = throw IllegalAccessError()

        override fun isWarnEnabled(): Boolean = throw IllegalAccessError()

        override fun trace(message: Any?): Unit = throw IllegalAccessError()

        override fun trace(message: Any?, vararg params: Any?) = throw IllegalAccessError()

        override fun trace(message: Any?, t: Throwable?): Unit = throw IllegalAccessError()

        override fun trace(message: Any?, t: Throwable?, vararg params: Any?) = throw IllegalAccessError()

        override fun isTraceEnabled(): Boolean = throw IllegalAccessError()
    }

    @Test
    fun trace_enable() {
        val expect = ":D"
        var received: Any? = null
        val target = Logger(object : DelegateWarrper() {
            override fun isTraceEnabled() = true
            override fun trace(message: Any?) {
                received = message
            }
        })
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

        val target = Logger(object : DelegateWarrper() {
            override fun isTraceEnabled(): Boolean = true
            override fun trace(message: Any?) {
                fail<Unit>()
            }

            override fun trace(message: Any?, t: Throwable?) {
                received = message
                receivedE = t
            }
        })
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
        val target = Logger(object : DelegateWarrper() {
            override fun isTraceEnabled(): Boolean = false
            override fun trace(message: Any?) {
                received = message
            }
        })
        target.t {
            expect
        }
        assertNull(received)
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
    fun debug_enable_throw() {
        val expect = ":D"
        val exception = Exception()

        var received: Any? = null
        var receivedE: Throwable? = null

        val target = Logger(object : DelegateWarrper() {
            override fun isDebugEnabled(): Boolean = true
            override fun debug(message: Any?) {
                fail<Unit>()
            }

            override fun debug(message: Any?, t: Throwable?) {
                received = message
                receivedE = t
            }
        })
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
    fun info_enable_throw() {
        val expect = ":D"
        val exception = Exception()

        var received: Any? = null
        var receivedE: Throwable? = null

        val target = Logger(object : DelegateWarrper() {
            override fun isInfoEnabled(): Boolean = true
            override fun info(message: Any?) {
                fail<Unit>()
            }

            override fun info(message: Any?, t: Throwable?) {
                received = message
                receivedE = t
            }
        })
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
    fun warn_enable_throw() {
        val expect = ":D"
        val exception = Exception()

        var received: Any? = null
        var receivedE: Throwable? = null

        val target = Logger(object : DelegateWarrper() {
            override fun isWarnEnabled(): Boolean = true
            override fun warn(message: Any?) {
                fail<Unit>()
            }

            override fun warn(message: Any?, t: Throwable?) {
                received = message
                receivedE = t
            }
        })
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