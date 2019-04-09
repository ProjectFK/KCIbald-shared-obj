package com.kcibald.objects

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ContentBasedTest {

    @Test
    fun authorSpec() {
        assertEquals(ContentBased.JsonKeySpec.author, "author")
    }

    @Test
    fun createTimeStampSpec() {
        assertEquals(ContentBased.JsonKeySpec.createTimeStamp, "create_time_stamp")
    }

    @Test
    fun updateTimeStampSpec() {
        assertEquals(ContentBased.JsonKeySpec.updateTimestamp, "update_time_stamp")
    }

    @Test
    fun contentSpec() {
        assertEquals(ContentBased.JsonKeySpec.content, "content")
    }

    @Test
    fun attachmentsSpec() {
        assertEquals(ContentBased.JsonKeySpec.attachments, "attachments")
    }



}