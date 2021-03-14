package com.gounaris.bist.preprocess

import java.lang.reflect.Method

class InvokeableMethod(
    val type: AnnotatedMethodType,
    val identifier: String,
    val instance: Any?, // null for static methods, here to be future proof
    val method: Method,
    val methodParameters: List<AnnotatedMethodParameter>
)

data class AnnotatedMethodParameter(
    val name: String,
    val type: Class<*>
)