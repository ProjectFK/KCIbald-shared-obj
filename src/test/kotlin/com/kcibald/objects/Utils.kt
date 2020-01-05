package com.kcibald.objects

operator fun <IN, OUT> java.util.function.Function<IN, OUT>.invoke(i: IN): OUT = this.apply(i)