package com.kcibald.services.files.impl

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ImageFileResolverClientImplTest {

    @Test
    fun translateImageTokenToURL_wrong_format_version_prefix() = runBlocking {
        assertNull(ImageFileResolverClientImpl.translateImageTokenToURL("23:d0-xxx"))
    }

    @Test
    fun translateImageTokenToURL_wrong_format_semi() = runBlocking {
        assertNull(ImageFileResolverClientImpl.translateImageTokenToURL("v1-d0-xxx"))
    }

    @Test
    fun translateImageTokenToURL_missing_version() = runBlocking {
        assertNull(ImageFileResolverClientImpl.translateImageTokenToURL("v2:d0-xxx"))
    }

    @Test
    fun translateImageTokenToURL_malformed_version() = runBlocking {
        assertNull(ImageFileResolverClientImpl.translateImageTokenToURL("vx:d0-xxx"))
    }

    @Test
    fun translateImageTokenToURL_v1_no_domain() = runBlocking {
        assertNull(ImageFileResolverClientImpl.translateImageTokenToURL("v1:xxxx"))
    }

    @Test
    fun translateImageTokenToURL_v1_wrong_format_missing_connect() = runBlocking {
        assertNull(ImageFileResolverClientImpl.translateImageTokenToURL("v1:d1xxxxx"))
    }

    @Test
    fun translateImageTokenToURL_v1_domain_not_found() = runBlocking {
        assertNull(ImageFileResolverClientImpl.translateImageTokenToURL("v1:d2-xxx"))
    }

    @Test
    fun translateImageTokenToURL_v1_malformed_version_nub() = runBlocking {
        assertNull(ImageFileResolverClientImpl.translateImageTokenToURL("v1:dx-xxx"))
    }

    @Test
    fun translateImageTokenToURL_v1() = runBlocking {
        assertEquals("cdn.kcibald.com/v1-xxx", ImageFileResolverClientImpl.translateImageTokenToURL("v1:d0-xxx"))
    }
}