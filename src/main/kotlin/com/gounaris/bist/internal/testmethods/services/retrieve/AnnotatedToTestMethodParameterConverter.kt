package com.gounaris.bist.internal.testmethods.services.retrieve

import com.gounaris.bist.internal.testmethods.TestMethodParameter
import com.gounaris.bist.preprocess.AnnotatedMethodParameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
internal class AnnotatedToTestMethodParameterConverter(
    @Autowired private val typeToAliasTranslator: TypeToAliasTranslator
) {
    fun convert(parameterAnnotated: AnnotatedMethodParameter) =
        TestMethodParameter(
            parameterAnnotated.name,
            parameterAnnotated.type,
            typeToAliasTranslator.translate(parameterAnnotated.type)
        )
}