package com.gounaris.bist.preprocess.scan

import com.gounaris.bist.external.annotations.ExposedTestParameter
import com.gounaris.bist.preprocess.AnnotatedMethodParameter
import java.lang.reflect.Method

internal class ExposedTestParametersScanner {
    fun getParameters(method: Method): List<AnnotatedMethodParameter> =
        method.parameters.map {
            AnnotatedMethodParameter(
                it.getAnnotation(ExposedTestParameter::class.java).name,
                it.type
            )
        }
}