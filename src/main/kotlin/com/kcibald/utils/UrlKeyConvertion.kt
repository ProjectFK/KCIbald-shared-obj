package com.kcibald.utils

import java.util.*

fun String.toURLKey(): String {
    val out = StringBuilder(length)
    for (c in this) {
        if (notNeedURLEncodingBitSet.get(c.toInt())) {
            out.append(c)
        } else {
            out.append("-")
        }
    }
    return out.toString()
}

private val notNeedURLEncodingBitSet = makeUrlDontNeedEncodingBitSet()

private fun makeUrlDontNeedEncodingBitSet(): BitSet {
    //        from java.net.URLEncoder
    val dontNeedEncoding = BitSet(256)
    var i: Int = 'a'.toInt()
    while (i <= 'z'.toInt()) {
        dontNeedEncoding.set(i)
        i++
    }
    i = 'A'.toInt()
    while (i <= 'Z'.toInt()) {
        dontNeedEncoding.set(i)
        i++
    }
    i = '0'.toInt()
    while (i <= '9'.toInt()) {
        dontNeedEncoding.set(i)
        i++
    }
    dontNeedEncoding.set('-'.toInt())
    dontNeedEncoding.set('_'.toInt())
    dontNeedEncoding.set('.'.toInt())
    dontNeedEncoding.set('*'.toInt())
    return dontNeedEncoding
}