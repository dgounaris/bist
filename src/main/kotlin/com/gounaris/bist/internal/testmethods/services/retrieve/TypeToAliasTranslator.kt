package com.gounaris.bist.internal.testmethods.services.retrieve

import org.springframework.stereotype.Component

@Component
internal class TypeToAliasTranslator {
    fun translate(clazz: Class<*>) =
        when (clazz) {
            String::class.java -> "string"
            else -> "string"
        }
}