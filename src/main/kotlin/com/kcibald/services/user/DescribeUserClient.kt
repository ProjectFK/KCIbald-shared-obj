package com.kcibald.services.user

import com.kcibald.objects.User
import com.kcibald.services.Describer
import com.kcibald.services.Result

interface DescribeUserClient: Describer<User> {
    fun describeUser(urlKey: String): Result<User>
}