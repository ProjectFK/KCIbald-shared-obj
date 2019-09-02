package com.kcibald.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class IDUtilTest {

    @Test
    fun decodeDBID() {
        assertEquals("XQnlUfIU1EA3-50e", IDUtil.encodeDBID("5d09e551f214d44037fb9d1e"))
        assertEquals("XQnlUfIU1EA3-50f", IDUtil.encodeDBID("5d09e551f214d44037fb9d1f"))
        assertEquals("XQnlUfIU1EA3-50g", IDUtil.encodeDBID("5d09e551f214d44037fb9d20"))
        assertEquals("XQnlUfIU1EA3-50k", IDUtil.encodeDBID("5d09e551f214d44037fb9d24"))
    }

    @Test
    fun encodeDBID() {
        assertEquals("5d09e551f214d44037fb9d1e", IDUtil.decodeDBID("XQnlUfIU1EA3-50e"))
        assertEquals("5d09e551f214d44037fb9d1f", IDUtil.decodeDBID("XQnlUfIU1EA3-50f"))
        assertEquals("5d09e551f214d44037fb9d20", IDUtil.decodeDBID("XQnlUfIU1EA3-50g"))
        assertEquals("5d09e551f214d44037fb9d24", IDUtil.decodeDBID("XQnlUfIU1EA3-50k"))
    }
}