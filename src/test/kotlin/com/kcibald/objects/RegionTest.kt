package com.kcibald.objects

import com.kcibald.objects.impl.CommentImpl
import com.kcibald.objects.impl.RegionImpl
import com.kcibald.objects.impl.now
import com.kcibald.utils.DirectCollection
import com.kcibald.utils.toURLKey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RegionTest {

    val comment = listOf<Comment>(
        CommentImpl(
            1,
            User.createDefault(
                "name",
                "name",
                "url",
                "signature"
            ),
            "content",
            now,
            now,
            listOf(
                Attachment.createDefault("attachment1", "attachment_name1"),
                Attachment.createDefault("attachment2", "attachment_name2")
            ),
            listOf(
                Comment.createDefault(
                    2,
                    User.createDefault(
                        "name",
                        "name",
                        "url",
                        "signature"
                    ), "reply1", now, now
                ),
                Comment.createDefault(
                    3,
                    User.createDefault(
                        "name",
                        "name",
                        "url",
                        "signature"
                    ), "reply1", now, now
                )
            )
        )
    )

    val posts = listOf(
        Post.createDefault(
            "title",
            User.createDefault(
                "name",
                "name",
                "url",
                "signature"
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
    val avatar = "avatars.kcibald.com/kadjfkajd"
    val childRegion = emptyList<Region>()

    val region = RegionImpl(
        name,
        urlKey,
        parent,
        description,
        avatar,
        DirectCollection(posts),
        childRegion
    )

    @Test
    fun createDefault() {
        val target = Region.createDefault(
            name,
            description,
            avatar,
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
            avatar
        )

        assertEquals(urlKey, target.urlKey)
        assertEquals(null, target.parent)
        assert(target.childRegion.isEmpty())
        assert(target.topPosts.currentContent.isEmpty())
    }


}