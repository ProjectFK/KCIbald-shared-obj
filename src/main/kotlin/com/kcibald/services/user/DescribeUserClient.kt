package com.kcibald.services.user

import com.kcibald.objects.User
import com.kcibald.services.Describer
import com.kcibald.services.Result
import com.kcibald.services.ServiceClient

interface DescribeUserClient: Describer<User>, ServiceClient {
    suspend fun describeUser(urlKey: String): Result<User>
}