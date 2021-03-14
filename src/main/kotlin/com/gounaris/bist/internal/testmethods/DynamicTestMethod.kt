package com.gounaris.bist.internal.testmethods

import java.lang.reflect.Method

data class DynamicTestMethod (
    val identifier: String,
    val instance: Any?, // null for static methods, here to be future proof
    val method: Method,
    val parameters: List<DynamicMethodParameter>
)

data class DynamicMethodParameter(
    val name: String,
    val type: Class<*>
)