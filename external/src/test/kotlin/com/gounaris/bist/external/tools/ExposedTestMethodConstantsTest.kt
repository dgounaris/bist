package com.gounaris.bist.external.tools

import org.junit.Assert
import org.junit.jupiter.api.Test

internal class ExposedTestMethodConstantsTest {
    @Test
    fun `parameterRegex_matchesSuccessfully`() {
        val text = "A \${N} B \${a b c} } {}"

        val matchResults = ExposedTestMethodConstants.parameterRegex.findAll(text)

        with (matchResults.iterator()) {
            Assert.assertEquals("N", this.next().groupValues[1])
            Assert.assertEquals("a b c", this.next().groupValues[1])
            Assert.assertEquals(false, this.hasNext())
        }
    }
}