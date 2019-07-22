package com.kcibald.utils

abstract class PageableCollection<T>(
    val hasNextPage: Boolean,
    val currentContent: List<T>
) {
    protected abstract val defaultAmount: Int

    abstract suspend fun getNextPage(
        amount: Int = defaultAmount,
        skip: Int = 0
    ): PageableCollection<T>?
}

abstract class KnownSizePageableCollection<T>(
    hasNextPage: Boolean,
    currentContent: List<T>,
    val totalSize: Int
): PageableCollection<T>(
    hasNextPage,
    currentContent
)

class DirectCollection<T>(content: List<T>) : KnownSizePageableCollection<T>(
    false,
    content,
    content.size
) {
    override val defaultAmount: Int = 0

    override suspend fun getNextPage(
        amount: Int,
        skip: Int
    ): PageableCollection<T>? = null
}