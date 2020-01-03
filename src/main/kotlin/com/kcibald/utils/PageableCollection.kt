package com.kcibald.utils

interface PageableCollection<T> {
    val hasNextPage: Boolean
    val currentContent: List<T>
    val queryMark: String?
    val totalSize: Int

    companion object {
        fun <T> directCollection(content: List<T>): PageableCollection<T> = PageableCollectionImpl(
            false,
            content,
            null,
            content.size
        )
    }

}

internal data class PageableCollectionImpl<T>(
    override val hasNextPage: Boolean,
    override val currentContent: List<T>,
    override val queryMark: String?,
    override val totalSize: Int
) : PageableCollection<T>
