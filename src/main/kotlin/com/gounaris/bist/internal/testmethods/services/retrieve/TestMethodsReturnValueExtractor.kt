package com.gounaris.bist.internal.testmethods.services.retrieve

import com.gounaris.bist.internal.testmethods.TestMethodReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component
internal class TestMethodsReturnValueExtractor(
    @Autowired private val typeToAliasTranslator: TypeToAliasTranslator
) {
    fun extractReturnValueFrom(method: Method) =
        TestMethodReturn(
            method.returnType,
            typeToAliasTranslator.translate(method.returnType)
        )
}