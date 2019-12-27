package com.kcibald.services.kcibald

import com.kcibald.objects.Attachment
import com.kcibald.objects.Comment
import com.kcibald.objects.Post
import com.kcibald.services.Result
import com.kcibald.services.ServiceClient
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions

interface KCIBALDMutationClient : ServiceClient {

    suspend fun createPost(
        regionUrlKey: URLKey,
        title: String,
        content: String,
        attachments: List<Attachment> = emptyList()
    ): Result<Post>

    suspend fun deletePost(
        regionUrlKey: URLKey,
        postUrlKey: URLKey
    ): Result<Post>

    suspend fun createCommentUnderPost(
        regionUrlKey: URLKey,
        postUrlKey: URLKey,
        content: String,
        replyTo: String,
        attachments: List<Attachment> = emptyList()
    ): Result<Comment>

    companion object {
        private const val DEFAULT_EVENTBUS_ADDRESS: String = "kcibald.mut.bs1"

        // unfinished
        @Suppress("UNUSED_PARAMETER")
        fun createDefault(
            vertx: Vertx,
            eventBusAddr: String = DEFAULT_EVENTBUS_ADDRESS,
            deliveryOptions: DeliveryOptions = DeliveryOptions()
        ): KCIBALDMutationClient = TODO()
    }

}