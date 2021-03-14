package com.gounaris.bist.external.validation

import java.lang.reflect.Method

internal class ExposedTestMethodParameterNamesValidator : ExposedTestMethodValidator {
    override fun validate(method: Method): Boolean = true
}