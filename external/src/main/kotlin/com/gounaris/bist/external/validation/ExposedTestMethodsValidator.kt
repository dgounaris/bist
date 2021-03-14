package com.gounaris.bist.external.validation

import com.gounaris.bist.external.annotations.ExposedTestGiven
import java.lang.reflect.Method

class ExposedTestMethodsValidator {
    // todo validate that all parameters have the ExposedTestParameter
    // todo validate identifier's parameters are all contained in ExposedTestParameters
    // todo validate ExposedTestParameters are all contained in identifier
    // todo validate unique identifier's parameter names (no need to validate unique ExposedTestParameters, it's ok if it's duplicated)
}

internal interface ExposedTestMethodValidator {
    fun validate(method: Method): Boolean

    fun getGivenAnnotation(method: Method): ExposedTestGiven? =
        method.getAnnotation(ExposedTestGiven::class.java)
}