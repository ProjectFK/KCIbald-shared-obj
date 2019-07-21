package com.kcibald.objects

interface ContentBased {
    val author: User
    val createTimeStamp: Timestamp
    val updateTimestamp: Timestamp?
    val content: String
}
