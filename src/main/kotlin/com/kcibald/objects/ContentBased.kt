package com.kcibald.objects

import com.kcibald.serilization.PubliclySerializable
import com.kcibald.serilization.serializeString
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

interface ContentBased : PubliclySerializable {
    val author: User
    val createTimeStamp: Timestamp
    val updateTimestamp: Timestamp
    val content: HTMLContent
    val attachments: List<AttachmentURL>

    override fun asPublicJson() = json {
        obj(
            JsonKeySpec.author to author.asPublicJson(),
            JsonKeySpec.content to content.asString(),
            JsonKeySpec.createTimeStamp to createTimeStamp,
            JsonKeySpec.updateTimestamp to updateTimestamp,
            JsonKeySpec.attachments to attachments.serializeString()
        )
    }

    object JsonKeySpec {
        const val author = "author"
        const val createTimeStamp = "create_time_stamp"
        const val updateTimestamp = "update_time_stamp"
        const val content = "content"
        const val attachments = "attachments"
    }

}
