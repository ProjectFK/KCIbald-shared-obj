package com.kcibald.objects

import com.kcibald.serilization.json.JsonSerializable
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf

interface ContentBased : JsonSerializable {
    val author: User
    val createTimeStamp: Timestamp
    val updateTimestamp: Timestamp
    val content: String
    val attachments: List<Attachment>


    override fun asJson(): JsonObject =
        jsonObjectOf(
            JsonKeySpec.author to author.asJson(),
            JsonKeySpec.content to content,
            JsonKeySpec.createTimeStamp to createTimeStamp,
            JsonKeySpec.updateTimestamp to updateTimestamp,
            JsonKeySpec.attachments to attachments
        )

    object JsonKeySpec {
        const val author = "author"
        const val createTimeStamp = "create_time_stamp"
        const val updateTimestamp = "update_time_stamp"
        const val content = "content"
        const val attachments = "attachments"
    }

}
