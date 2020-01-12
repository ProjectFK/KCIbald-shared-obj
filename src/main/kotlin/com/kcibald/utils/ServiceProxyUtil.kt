package com.kcibald.utils

import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.serviceproxy.ServiceException

fun <T : Any?> Handler<AsyncResult<T>>.notFound() {
    handle(ServiceException.fail(404, "Not-Found"))
}
