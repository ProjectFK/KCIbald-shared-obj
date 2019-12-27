package com.kcibald.utils

abstract class PageableCollection<T>(
    val hasNextPage: Boolean,
    val currentContent: List<T>
) {
    protected abstract val defaultAmount: Int
    abstract val queryMark: String?

    abstract suspend fun getNextPage(
        amount: Int = defaultAmount,
        skip: Int = 0
    ): PageableCollection<T>?
}

abstract class KnownSizePageableCollection<T>(
    hasNextPage: Boolean,
    currentContent: List<T>,
    val totalSize: Int
) : PageableCollection<T>(
    hasNextPage,
    currentContent
)

class DirectCollection<T>(content: List<T>) : KnownSizePageableCollection<T>(
    false,
    content,
    content.size
) {
    override val defaultAmount: Int = 0
    override val queryMark: String? = null

    override suspend fun getNextPage(
        amount: Int,
        skip: Int
    ): PageableCollection<T>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DirectCollection<*>

        if (other.currentContent != this.currentContent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currentContent.hashCode()
        result = 31 * result + javaClass.hashCode()
        return result
    }
}