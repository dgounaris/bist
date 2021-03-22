package com.gounaris.bist.internal.testmethods.services.retrieve

import com.gounaris.bist.internal.testmethods.TestMethodParameter
import com.gounaris.bist.internal.testmethods.TestMethodReturn
import com.gounaris.bist.preprocess.AnnotatedMethodsRetriever
import com.gounaris.bist.preprocess.InvokeableMethod
import com.gounaris.bist.preprocess.AnnotatedMethodParameter
import com.gounaris.bist.preprocess.AnnotatedMethodType
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.jupiter.api.Test
import kotlin.reflect.jvm.javaMethod

internal class TestMethodsRetrievalByTypeServiceImplTest {
    @Test
    fun `retrieveNames_forGiven_successful`() {
        // workaround because directly mocking java.lang.reflect.Method is not allowed
        val mockMethod = this::retrieveNames_forGiven_successful.javaMethod!!
        val methodParam = AnnotatedMethodParameter("c", String::class.java)
        val retriever = mock<AnnotatedMethodsRetriever>().also {
            doReturn(
                listOf(
                    InvokeableMethod(
                        AnnotatedMethodType.GIVEN,
                        "A B \${c}",
                        null,
                        mockMethod,
                        listOf(
                            methodParam
                        )
                    )
                )
            ).whenever(it).getMethodsByType(AnnotatedMethodType.GIVEN)
        }
        val paramExtractor = mock<AnnotatedToTestMethodParameterConverter>().also {
            doReturn(
                TestMethodParameter("c", String::class.java, "string")
            ).whenever(it).convert(methodParam)
        }
        val returnValueExtractor = mock<TestMethodsReturnValueExtractor>().also {
            doReturn(
                TestMethodReturn(String::class.java, "string")
            ).whenever(it).extractReturnValueFrom(mockMethod)
        }
        val retrievalService = TestMethodsRetrievalByTypeServiceImpl(
            "", retriever, paramExtractor, returnValueExtractor
        )

        val result = retrievalService.retrieve(
            TestMethodsRetrievalByTypeService.TestMethodsRetrievalCommand(
                TestMethodsRetrievalByTypeService.TestMethodType.GIVEN
            )
        )

        Assert.assertEquals(1, result.size)
    }
}