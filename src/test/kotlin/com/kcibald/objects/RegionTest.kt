package com.kcibald.objects

import com.kcibald.objects.Attachment.Companion.createDefault
import com.kcibald.objects.User.Companion.createDefault
import com.kcibald.objects.impl.CommentImpl
import com.kcibald.objects.impl.RegionImpl
import com.kcibald.objects.impl.now
import com.kcibald.utils.PageableCollection
import com.kcibald.utils.toURLKey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RegionTest {

    val comment = listOf<Comment>(
        CommentImpl(
            1,
            createDefault(
                "name",
                "name",
                File.withIdentifier("url"), "signature"
            ),
            "content",
            now,
            now,
            listOf(
                createDefault(File.withIdentifier("attachment1"), "attachment_name1"),
                createDefault(File.withIdentifier("attachment2"), "attachment_name2")
            ),
            listOf(
                Comment.createDefault(
                    2,
                    createDefault(
                        "name",
                        "name",
                        File.withIdentifier("url"), "signature"
                    ), "reply1", now, now
                ),
                Comment.createDefault(
                    3,
                    createDefault(
                        "name",
                        "name",
                        File.withIdentifier("url"), "signature"
                    ), "reply1", now, now
                )
            )
        )
    )

    val posts = listOf(
        Post.createDefault(
            "title",
            createDefault(
                "name",
                "name",
                File.withIdentifier("url"), "signature"
            ),
            "content",
            "name",
            now,
            now,
            emptyList(),
            comment,
            "title".toURLKey()
        )
    )


    val name = "name"
    val urlKey = name.toURLKey()
    val parent = null
    val description = "description"
    val avatar = File.withIdentifier("avatars.kcibald.com/kadjfkajd")
    val childRegion = emptyList<Region>()
    val colors = Region.Colors("", "")

    val region = RegionImpl(
        name,
        urlKey,
        parent,
        description,
        avatar,
        PageableCollection.directCollection(posts),
        childRegion,
        colors
    )

    @Test
    fun createDefault() {
        val target = Region.createDefault(
            name,
            description,
            avatar,
            colors,
            urlKey,
            parent,
            posts,
            childRegion
        )
        assertEquals(region, target)
    }

    @Test
    fun createDefault_defaultOptions() {
        val target = Region.createDefault(
            name,
            description,
            avatar,
            colors
        )

        assertEquals(urlKey, target.urlKey)
        assertEquals(null, target.parent)
        assert(target.childRegion.isEmpty())
        assert(target.topPosts.currentContent.isEmpty())
    }

    @Test
    fun json() {
        val json = Region.vertxGenToJson(region)
        println(json)
        assertEquals(region, Region.vertxGenFromJson(json))
    }


}