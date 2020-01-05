package com.kcibald.objects.impl

import com.kcibald.objects.File
import com.kcibald.objects.invoke
import com.kcibald.services.files.ImageFileResolverClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FileImplTest {

    val identifier = "v1:d0-abcd"
    val target = FileImpl(identifier)

    @Test
    fun resolveAsURL() {
        assertEquals(ImageFileResolverClient.getInstance().translateImageTokenToURL(identifier), target.resolveAsURL())
    }

    @Test
    fun getIdentifier() {
        assertEquals(identifier, target.identifier)
    }

    @Test
    fun json() {
        val json = File.vertxGenToJson(target)
        assertEquals(target, File.vertxGenFromJson(json))
    }
}