package com.gounaris.bist.preprocess.scan

import com.gounaris.bist.external.annotations.ExposedTestGiven
import com.gounaris.bist.external.annotations.description
import com.gounaris.bist.preprocess.InvokeableMethod
import com.gounaris.bist.preprocess.AnnotatedMethodType
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import java.io.File
import java.lang.reflect.Modifier
import java.net.URLClassLoader

internal class ExposedTestMethodScanner private constructor(
    jarPath: String,
    private val exposedTestParametersScanner: ExposedTestParametersScanner
) {
    internal constructor(jarPath: String): this(jarPath, ExposedTestParametersScanner())

    private val jarClassLoader = URLClassLoader(
        arrayOf(File(jarPath).toURI().toURL()),
        this::class.java.classLoader
    )
    private val reflections = Reflections(jarClassLoader, MethodAnnotationsScanner())

    internal val methods = retrieveExposedTestMethods()

    private fun retrieveExposedTestMethods() : List<InvokeableMethod> {
        val given = reflections.getMethodsAnnotatedWith(ExposedTestGiven::class.java)
            .filter { Modifier.isStatic(it.modifiers) }
        return given.map {
            InvokeableMethod(
                AnnotatedMethodType.GIVEN,
                it.getAnnotation(ExposedTestGiven::class.java).description(),
                null,
                it,
                exposedTestParametersScanner.getParameters(it)
            )
        }
    }
}