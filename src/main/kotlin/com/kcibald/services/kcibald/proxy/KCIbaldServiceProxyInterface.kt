package com.kcibald.services.kcibald.proxy

import com.kcibald.objects.Post
import com.kcibald.objects.Region
import com.kcibald.services.PageableFetchConfig
import com.kcibald.services.kcibald.URLKey
import com.kcibald.utils.PageableCollectionTypeErased
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions

typealias PageableCollectionOfMinimizedPost = PageableCollectionTypeErased
typealias PageableCollectionOfComment = PageableCollectionTypeErased

@VertxGen
@ProxyGen
interface KCIbaldServiceProxyInterface {
    fun describeRegion(
        regionUrlKey: URLKey,
        topPostFetchConfig: PageableFetchConfig,
        handler: Handler<AsyncResult<Region>>
    )

    fun listPostsUnderRegion(
        regionUrlKey: URLKey,
        fetchConfig: PageableFetchConfig,
        handler: Handler<AsyncResult<PageableCollectionOfMinimizedPost>>
    )

    fun describePost(
        regionUrlKey: URLKey,
        postUrlKey: URLKey,
        commentFetchConfig: PageableFetchConfig,
        handler: Handler<AsyncResult<Post>>
    )

    fun listCommentsUnderPost(
        regionUrlKey: URLKey,
        postUrlKey: URLKey,
        fetchConfig: PageableFetchConfig,
        handler: Handler<AsyncResult<PageableCollectionOfComment>>
    )

    companion object {
        fun createClient(
            vertx: Vertx,
            address: String,
            deliveryOptions: DeliveryOptions = DeliveryOptions()
        ): KCIbaldServiceProxyInterface = ServiceProxyInterfaceVertxEBProxy(vertx, address, deliveryOptions)
    }

}