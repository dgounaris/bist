package com.gounaris.bist.preprocess

import com.gounaris.bist.preprocess.scan.ExposedTestMethodScanner
import org.junit.Assert
import org.junit.jupiter.api.Test

internal class ExposedTestMethodsScannerTest {
    @Test
    fun `retrieveExposedTestMethods_withExistingJar_returns`() {
        val scanner = ExposedTestMethodScanner("../dogfood/build/libs/dogfood.jar")

        val result = scanner.methods

        Assert.assertEquals(3, result.size)
        with(result.single { it.identifier == "Given a 0" }) {
            Assert.assertEquals(0, this.method.invoke(this.instance))
        }
        with(result.single { it.identifier == "Given a 1" }) {
            Assert.assertEquals(1L, this.method.invoke(this.instance))
        }
    }
}