package com.gounaris.bist.preprocess

import com.gounaris.bist.preprocess.scan.ExposedTestMethodScanner

interface AnnotatedMethodsRetriever {
    fun getMethodsByType(type: AnnotatedMethodType): List<InvokeableMethod>
    fun getMethodByIdentifier(identifier: String): InvokeableMethod?
}

class AnnotatedMethodsRetrieverImpl internal constructor(
    private val scanner: ExposedTestMethodScanner
) : AnnotatedMethodsRetriever {

    constructor(jarPath: String) : this(ExposedTestMethodScanner(jarPath))

    private val methodsByType = scanner.methods.groupBy { it.type }

    override fun getMethodsByType(type: AnnotatedMethodType): List<InvokeableMethod> =
        methodsByType[type] ?: emptyList()

    override fun getMethodByIdentifier(identifier: String): InvokeableMethod? =
        scanner.methods.firstOrNull { it.identifier == identifier }
}