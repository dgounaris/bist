package com.gounaris.bist.internal.testmethods.services.dynamicresolve

import com.gounaris.bist.internal.testmethods.DynamicMethodParameter
import com.gounaris.bist.internal.testmethods.DynamicTestMethod
import com.gounaris.bist.preprocess.AnnotatedMethodsRetriever
import com.gounaris.bist.preprocess.AnnotatedMethodsRetrieverImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

interface DynamicMethodResolver {
    fun resolve(command: ReflectiveMethodResolveCommand): DynamicTestMethod?

    data class ReflectiveMethodResolveCommand(val identifier: String)
}

@Service
class DynamicMethodResolverImpl(
    @Value("\${testmethods.jarpath}") jarPath: String,
    private val annotatedMethodsRetriever: AnnotatedMethodsRetriever
        = AnnotatedMethodsRetrieverImpl(jarPath)
) : DynamicMethodResolver {
    override fun resolve(command: DynamicMethodResolver.ReflectiveMethodResolveCommand) =
        annotatedMethodsRetriever.getMethodByIdentifier(command.identifier)?.let {
            DynamicTestMethod(
                it.identifier,
                it.instance,
                it.method,
                it.methodParameters.map { mp -> DynamicMethodParameter(mp.name, mp.type) }
            )
        }

}