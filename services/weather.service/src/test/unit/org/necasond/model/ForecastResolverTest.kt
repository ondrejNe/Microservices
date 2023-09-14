package org.necasond.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ForecastResolverTest {

    @Test
    fun `resolve should return non-null value`() {
        val result = ForecastResolver.resolve("TestString")
        assertNotNull(result)
    }

    @Test
    fun `resolve should return different results for different inputs`() {
        val result1 = ForecastResolver.resolve("TestString1")
        val result2 = ForecastResolver.resolve("TestString2")
        assertNotEquals(result1, result2)
    }

    @Test
    fun `resolve should return the same result for the same input`() {
        val result1 = ForecastResolver.resolve("TestString")
        val result2 = ForecastResolver.resolve("TestString")
        assertEquals(result1, result2)
    }
}
