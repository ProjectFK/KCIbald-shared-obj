package com.kcibald.objects.utils

import com.kcibald.objects.impl.sharedMapper
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.contains
import org.junit.jupiter.api.Test

internal class UtilsTest {

    @Test
    fun ensure_mapper_has_kotlin_module() {
        assertThat(sharedMapper.registeredModuleIds, `is`(contains<Any>("com.fasterxml.jackson.module.kotlin.KotlinModule")))
    }
}