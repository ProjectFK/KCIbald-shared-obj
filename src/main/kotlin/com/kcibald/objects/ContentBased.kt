package com.kcibald.objects

interface ContentBased {
    val author: User
    val createTimestamp: Timestamp
    val updateTimestamp: Timestamp?
    val content: String
}
