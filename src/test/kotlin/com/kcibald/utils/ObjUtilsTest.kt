package com.kcibald.utils

import com.kcibald.objects.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ObjUtilsTest {

    @Test
    fun minimize() {
        val attachments = listOf(
            Attachment.createDefault(
                File.withIdentifier(""),
                ""
            )
        )
        val target = Post.createDefault(
            "title",
            User.createDefault(
                "name",
                "name",
                File.withIdentifier("avatar"), "signature"
            ),
            "content",
            urlKey = "",
            parentRegionKey = "",
            attachments = attachments
        )

        val expected = MinimizedPost.createDefault(
            "title",
            "content",
            User.createDefault(
                "name",
                "name",
                File.withIdentifier("avatar"), "signature"
            ),
            0,
            "",
            ""
        )

        assertEquals(expected, ObjUtils.minimize(target))
    }
}