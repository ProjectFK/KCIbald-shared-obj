package com.kcibald.utils

import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.*

fun runInCoroutineFromVertx(
    dispatcher: CoroutineDispatcher = getDispatcher(),
    block: suspend CoroutineScope.() -> Unit
) = GlobalScope.launch(dispatcher, block = block)

fun <T> runInCoroutineFromVertxAsync(
    dispatcher: CoroutineDispatcher = getDispatcher(),
    block: suspend CoroutineScope.() -> T
) = GlobalScope.async(dispatcher, block = block)

private fun getDispatcher(): CoroutineDispatcher {
    val currentContext = Vertx.currentContext() ?: throw IllegalStateException("Not in vertx context")
    return currentContext.dispatcher()
}
