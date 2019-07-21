package com.kcibald.objects

import com.kcibald.serilization.json.JsonSerializable
import com.kcibald.serilization.keyspecs.ContentBasedKeySpec
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.jsonObjectOf

interface ContentBased : JsonSerializable {
    val author: User
    val createTimeStamp: Timestamp
    val updateTimestamp: Timestamp
    val content: String

    override fun asJson(): JsonObject =
        jsonObjectOf(
            ContentBasedKeySpec.author to author.asJson(),
            ContentBasedKeySpec.content to content,
            ContentBasedKeySpec.createTimeStamp to createTimeStamp,
            ContentBasedKeySpec.updateTimestamp to updateTimestamp
        )

}
