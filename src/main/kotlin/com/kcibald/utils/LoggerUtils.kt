package com.kcibald.utils

import io.vertx.core.logging.Logger

inline fun Logger.d(messageSupplier: () -> String) {
    if (this.isDebugEnabled) {
        this.debug(messageSupplier())
    }
}
inline fun Logger.i(messageSupplier: () -> String) {
    if (this.isInfoEnabled) {
        this.info(messageSupplier())
    }
}
inline fun Logger.w(messageSupplier: () -> String) {
    if (this.isWarnEnabled) {
        this.warn(messageSupplier())
    }
}