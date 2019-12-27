package com.kcibald.services.kcibald

import com.kcibald.objects.Comment
import com.kcibald.objects.MinimizedPost
import com.kcibald.objects.Post
import com.kcibald.objects.Region
import com.kcibald.services.PageableFetchConfig
import com.kcibald.services.Result
import com.kcibald.services.defaultPageableFetchConfig
import com.kcibald.utils.PageableCollection
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions

typealias URLKey = String

interface KCIBALDClient {
    suspend fun describeRegion(
        regionUrlKey: URLKey,
        topPostFetchConfig: PageableFetchConfig = defaultPageableFetchConfig
    ): Result<Region>

    suspend fun listPostsUnderRegion(
        regionUrlKey: URLKey,
        fetchConfig: PageableFetchConfig
    ): Result<PageableCollection<MinimizedPost>>

    suspend fun describePost(
        regionUrlKey: URLKey,
        postUrlKey: URLKey,
        commentFetchConfig: PageableFetchConfig = defaultPageableFetchConfig
    ): Result<Post>

    suspend fun listCommentsUnderPost(
        regionUrlKey: URLKey,
        postUrlKey: URLKey,
        fetchConfig: PageableFetchConfig
    ): Result<PageableCollection<Comment>>

    companion object {
        private const val DEFAULT_EVENTBUS_ADDRESS: String = "kcibald.acs.bs1"

        // unfinished
        @Suppress("UNUSED_PARAMETER")
        fun createDefault(
            vertx: Vertx,
            eventBusAddr: String = DEFAULT_EVENTBUS_ADDRESS,
            deliveryOptions: DeliveryOptions = DeliveryOptions()
        ): KCIBALDClient = TODO()
    }
}
