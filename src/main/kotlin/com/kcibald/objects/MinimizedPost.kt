package com.kcibald.objects

import com.kcibald.serilization.keyspecs.PostJsonKeySpec
import io.vertx.core.json.JsonObject

interface MinimizedPost : ContentBased {
    val id: String
    val title: String
    val urlKey: String
    val parentRegionUrlKey: String
    val commentCount: Int

    override fun asJson(): JsonObject = super
        .asJson()
        .put(PostJsonKeySpec.id, id)
        .put(PostJsonKeySpec.urlKey, urlKey)
        .put(PostJsonKeySpec.parentRegionUrlKey, parentRegionUrlKey)
        .put(PostJsonKeySpec.commentCount, commentCount)
}