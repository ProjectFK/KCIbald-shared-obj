package com.kcibald.services.kcibald

import com.kcibald.objects.*
import com.kcibald.services.PageableFetchConfig
import com.kcibald.services.Result
import com.kcibald.services.defaultPageableFetchConfig
import com.kcibald.utils.PageableCollection

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

    suspend fun listCommentsUnderPost(
        regionUrlKey: URLKey,
        postUrlKey: URLKey,
        fetchConfig: PageableFetchConfig
    ): Result<PageableCollection<Comment>>

    suspend fun createCommentUnderPost(
        regionUrlKey: URLKey,
        postUrlKey: URLKey,
        content: String,
        attachments: List<Attachment> = emptyList()
    ): Result<Comment>

}
