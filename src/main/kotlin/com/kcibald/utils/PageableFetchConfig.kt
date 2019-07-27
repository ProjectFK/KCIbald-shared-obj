package com.kcibald.utils

data class PageableFetchConfig(
    val count: Int = DEFAULT_POST_PER_PAGE,
    val skip: Int = 0,
    val queryMark: String? = null
) {
    companion object {
        const val DEFAULT_POST_PER_PAGE: Int = 20
    }
}

val defaultPageableFetchConfig = PageableFetchConfig()