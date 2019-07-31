package com.kcibald.objects

import com.kcibald.objects.impl.RegionImpl
import com.kcibald.utils.DirectCollection
import com.kcibald.utils.PageableCollection
import com.kcibald.utils.toURLKey

interface Region {
    val name: String
    val urlKey: String
    val parent: Region?
    val description: String
    val avatar: File
    val topPosts: PageableCollection<MinimizedPost>
    val childRegion: List<Region>
    val colors: Colors

    data class Colors(
        val left: String,
        val right: String
    )

    companion object {
        fun createDefault(
            name: String,
            description: String,
            avatar: File,
            colors: Colors,
            urlKey: String = name.toURLKey(),
            parent: Region? = null,
            topPosts: List<MinimizedPost> = emptyList(),
            childRegion: List<Region> = emptyList()
        ): Region = RegionImpl(
            name,
            urlKey,
            parent,
            description,
            avatar,
            DirectCollection(topPosts),
            childRegion,
            colors
        )
    }
}