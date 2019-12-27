@file:Suppress("DEPRECATION")

package com.kcibald.utils

import io.vertx.core.logging.Logger

fun Logger.t(throwable: Throwable? = null, messageSupplier: () -> String) {
    if (this.isTraceEnabled) {
        if (throwable == null)
            this.trace(messageSupplier())
        else
            this.trace(messageSupplier(), throwable)
    }
}

fun Logger.d(throwable: Throwable? = null, messageSupplier: () -> String) {
    if (this.isDebugEnabled) {
        if (throwable == null)
            this.debug(messageSupplier())
        else
            this.debug(messageSupplier(), throwable)
    }
}

fun Logger.i(throwable: Throwable? = null, messageSupplier: () -> String) {
    if (this.isInfoEnabled) {
        if (throwable == null)
            this.info(messageSupplier())
        else
            this.info(messageSupplier(), throwable)
    }
}

fun Logger.w(throwable: Throwable? = null, messageSupplier: () -> String) {
    if (this.isWarnEnabled) {
        if (throwable == null)
            this.warn(messageSupplier())
        else
            this.warn(messageSupplier(), throwable)
    }
}