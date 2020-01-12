package com.kcibald.utils

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.convertValue
import com.kcibald.objects.impl.sharedMapper
import io.vertx.codegen.annotations.Mapper
import io.vertx.core.json.JsonObject
import java.util.function.Function

// There may need to be check for validity in json deserialize process
@JsonDeserialize(`as` = PageableCollectionImpl::class)
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

        fun <T> multiPageCollection(
            currentContent: List<T>,
            queryMark: String,
            totalSize: Int
        ): PageableCollection<T> {
            require(totalSize > currentContent.size) { "invalid size" }
            return PageableCollectionImpl(true, currentContent, queryMark, totalSize)
        }

    }

}

fun <T> PageableCollection<T>.eraseType(): PageableCollectionTypeErased = object : PageableCollectionTypeErased {
    override val hasNextPage: Boolean
        get() = this@eraseType.hasNextPage
    @Suppress("UNCHECKED_CAST")
    override val currentContent: List<Any>
        get() = this@eraseType.currentContent as List<Any>
    override val queryMark: String?
        get() = this@eraseType.queryMark
    override val totalSize: Int
        get() = this@eraseType.totalSize

    override fun toString() = "PageableCollectionTypeErasedDelegator(${this@eraseType})"
}

/**
 * for code gen, codegen do not allow type para
 */
@JsonDeserialize(`as` = DumbPageableCollectionTypeErasedImpl::class)
interface PageableCollectionTypeErased : PageableCollection<Any> {
    companion object {
        @field:Mapper
        @JvmField
        val vertxGenToJson: Function<PageableCollectionTypeErased, JsonObject> = Function {
            JsonObject(sharedMapper.convertValue<Map<String, Any>>(it))
        }

        @field:Mapper
        @JvmField
        val vertxGenFromJson: Function<JsonObject, PageableCollectionTypeErased> = Function {
            sharedMapper.convertValue(it.map)
        }
    }
}

internal data class PageableCollectionImpl<T>(
    override val hasNextPage: Boolean,
    override val currentContent: List<T>,
    override val queryMark: String?,
    override val totalSize: Int
) : PageableCollection<T>

internal data class DumbPageableCollectionTypeErasedImpl(
    override val hasNextPage: Boolean,
    override val currentContent: List<Any>,
    override val queryMark: String?,
    override val totalSize: Int
) : PageableCollectionTypeErased

inline fun <reified T> PageableCollectionTypeErased.cast(): PageableCollection<T> {
    // cannot check in runtime anyway...
    @Suppress("UNCHECKED_CAST")
    val castedList = this.currentContent as List<T>
    return if (this.hasNextPage) {
        PageableCollection.directCollection(castedList)
    } else {
        PageableCollection.multiPageCollection(
            castedList,
            this.queryMark!!,
            this.totalSize
        )
    }
}
