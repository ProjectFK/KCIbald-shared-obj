package com.kcibald.objects.impl

import com.kcibald.objects.Attachment.Companion.createDefault
import com.kcibald.objects.Comment
import com.kcibald.objects.File
import com.kcibald.objects.Post
import com.kcibald.objects.Region
import com.kcibald.objects.User.Companion.createDefault
import com.kcibald.utils.DirectCollection
import com.kcibald.utils.toURLKey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class RegionImplTest {

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
        DirectCollection(posts),
        childRegion,
        colors
    )

    @Test
    fun getTopPosts_hasNoNextPage() {
        val topPosts = region.topPosts
        assertFalse(topPosts.hasNextPage)
    }

    @Test
    fun getTopPosts_currentContent() {
        val topPosts = region.topPosts
        assertEquals(posts, topPosts.currentContent)
    }

    @Test
    fun getName() {
        assertEquals(name, region.name)
    }

    @Test
    fun getUrlKey() {
        assertEquals(urlKey, region.urlKey)
    }

    @Test
    fun getParent() {
        assertEquals(parent, region.parent)
    }

    @Test
    fun getDescription() {
        assertEquals(description, region.description)
    }

    @Test
    fun getAvatar() {
        assertEquals(avatar, region.avatar)
    }

    @Test
    fun getChildRegion() {
        assertEquals(childRegion, region.childRegion)
    }

    @Test
    fun getColors() {
        assertEquals(colors, region.colors)
    }

}