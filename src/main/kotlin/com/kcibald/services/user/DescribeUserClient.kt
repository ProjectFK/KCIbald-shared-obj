package com.kcibald.services.user

import com.kcibald.objects.User
import com.kcibald.services.Describer
import com.kcibald.services.Result
import com.kcibald.services.ServiceClient
import com.kcibald.services.user.impl.DescribeUserClientImpl
import io.vertx.core.Vertx

interface DescribeUserClient: Describer<User>, ServiceClient {
    suspend fun describeUser(urlKey: String): Result<User>

    companion object {
        fun createDefault(
            vertx: Vertx,
            eventBusAddress: String = DEFAULT_EVENT_BUS_ADDRESS
        ): DescribeUserClient = DescribeUserClientImpl(vertx, eventBusAddress)

        internal const val DEFAULT_EVENT_BUS_ADDRESS = "kcibald.user.describe"
    }
}