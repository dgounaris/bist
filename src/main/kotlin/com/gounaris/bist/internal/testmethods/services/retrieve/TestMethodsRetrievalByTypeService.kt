package com.gounaris.bist.internal.testmethods.services.retrieve

import com.gounaris.bist.internal.testmethods.TestMethod
import com.gounaris.bist.preprocess.AnnotatedMethodsRetriever
import com.gounaris.bist.preprocess.AnnotatedMethodsRetrieverImpl
import com.gounaris.bist.preprocess.AnnotatedMethodType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

interface TestMethodsRetrievalByTypeService {
    fun retrieve(command: TestMethodsRetrievalCommand): List<TestMethod>

    data class TestMethodsRetrievalCommand(val type: TestMethodType)
    enum class TestMethodType { GIVEN, WHEN, THEN }
}

@Service
internal class TestMethodsRetrievalByTypeServiceImpl(
    @Value("\${testmethods.jarpath}") jarPath: String,
    private val annotatedMethodsRetriever: AnnotatedMethodsRetriever
        = AnnotatedMethodsRetrieverImpl(jarPath),
    @Autowired private val annotatedToTestMethodParameterConverter: AnnotatedToTestMethodParameterConverter,
    @Autowired private val testMethodsReturnValueExtractor: TestMethodsReturnValueExtractor
) : TestMethodsRetrievalByTypeService {
    override fun retrieve(command: TestMethodsRetrievalByTypeService.TestMethodsRetrievalCommand): List<TestMethod> {
        val type = AnnotatedMethodType.valueOf(command.type.toString())
        return annotatedMethodsRetriever.getMethodsByType(type).map {
            TestMethod(
                it.identifier,
                it.methodParameters.map { param -> annotatedToTestMethodParameterConverter.convert(param) },
                testMethodsReturnValueExtractor.extractReturnValueFrom(it.method)
            )
        }
    }
}