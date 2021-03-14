package com.gounaris.bist.internal.testmethods

data class TestMethod(
    val identifier: String,
    val parameters: List<TestMethodParameter>,
    val returnValue: TestMethodReturn
)

data class TestMethodParameter(
    val name: String,
    val type: Class<*>,
    val typeAlias: String
)

data class TestMethodReturn(
    val type: Class<*>,
    val typeAlias: String
)