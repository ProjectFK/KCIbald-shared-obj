package com.kcibald.services

import org.junit.jupiter.api.Test

internal class ResultTest {

    @Test
    fun success() {
        val r = Result.success("")
        assert(r is Result.Success)
    }

    @Test
    fun notFound() {
        val r = Result.notFound<String>()
        assert(r is Result.NotFound)
    }

    @Test
    fun failure() {
        val r = Result.failure<String>("")
        assert(r is Result.Failure)
    }

}