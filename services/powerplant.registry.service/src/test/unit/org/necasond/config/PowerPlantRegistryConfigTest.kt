package org.necasond.config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.necasond.logging.ILoggable
import org.necasond.redis.RedisEnv

class PowerPlantRegistryConfigTest : ILoggable {

    @Test
fun `load should return default values when environment variables are missing`() {
    val mockEnvProvider: EnvProvider = mock(EnvProvider::class.java)
    whenever(mockEnvProvider.getEnv()).thenReturn(emptyMap())

    val config = PowerPlantRegistryConfig(envProvider = mockEnvProvider)
    val expectedConfig = PowerPlantRegistryConfig(
        redis = RedisEnv(address = "127.0.0.1", port = "6379", pass = "WF4tUFtQNi")
    )

    val actualConfig = config.load()

    assertEquals(expectedConfig, actualConfig)
}

    @Test
    fun `load should return environment values when they are present`() {
        val envMap = mapOf(
            "redis.address" to "192.168.1.1",
            "redis.port" to "6380",
            "redis.pass" to "newPass"
        )

        val mockEnvProvider: EnvProvider = mock(EnvProvider::class.java)
        whenever(mockEnvProvider.getEnv()).thenReturn(envMap)

        val config = PowerPlantRegistryConfig(envProvider = mockEnvProvider)
        val expectedConfig = PowerPlantRegistryConfig(
            redis = RedisEnv(address = "192.168.1.1", port = "6380", pass = "newPass")
        )

        val actualConfig = config.load()

        assertEquals(expectedConfig, actualConfig)
    }

    @Test
    fun `load should partially update from environment variables when some are missing`() {
        val envMap = mapOf(
            "redis.address" to "192.168.1.1"
            // Intentionally omitting port and pass
        )

        val mockEnvProvider: EnvProvider = mock(EnvProvider::class.java)
        whenever(mockEnvProvider.getEnv()).thenReturn(envMap)

        val config = PowerPlantRegistryConfig(envProvider = mockEnvProvider)
        val expectedConfig = PowerPlantRegistryConfig(
            redis = RedisEnv(address = "192.168.1.1", port = "6379", pass = "WF4tUFtQNi")
        )

        val actualConfig = config.load()

        assertEquals(expectedConfig, actualConfig)
    }
}
