package com.kcibald.services.kcibald.proxy

import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import org.junit.jupiter.api.Test

internal class KCIbaldServiceProxyInterfaceKtTest {

    @Test
    fun createClient() {
        // make sure the class is present, not throwing ClassNotFoundError or vise versa
        createKCIBALDInterfaceClient(Vertx.vertx(), "", DeliveryOptions().addHeader("header!", "oh yeah!"))
        createKCIBALDInterfaceClient(Vertx.vertx(), "")
    }
}