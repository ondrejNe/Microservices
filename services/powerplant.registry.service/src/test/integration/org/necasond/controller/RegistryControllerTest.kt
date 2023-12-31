package org.necasond.controller

import org.necasond.config.PowerPlantRegistryEnv
import org.necasond.redis.RedisClientFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class RegistryControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var redisClientFactory: RedisClientFactory

    @MockBean
    lateinit var mockEnv: PowerPlantRegistryEnv

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `should add powerplant successfully`() = runTest {
//        val powerplant = Powerplant(systemId = 1L, plantId = "Plant1")
//        val mockClient: KredsClient = mock{
//            onBlocking { upload(any())) } doReturn Unit
//        }
//
//        whenever(redisClientFactory.create(eq("127.0.0.1"), eq("6379"))).thenReturn(mockClient)
//        mockMvc.post("http://localhost:8080/powerplant") {
//            contentType = MediaType.APPLICATION_JSON
//            content = """
//                {
//                    "systemId": 111,
//                    "plantId": "Plant1"
//                }
//            """.trimIndent()
//        }.andExpect {
//            status { isOk() }
//        }
//    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `should delete powerplant successfully`() = runTest {
//        val mockClient: KredsClient = mock()
//
//        whenever(redisClientFactory.create(anyString(), anyString())).thenReturn(mockClient)
//        whenever(mockClient.delete(anyString(), anyString())).thenReturn(Unit)
//
//        mockMvc.delete("/deletePlantById/{plantId}", "Plant1")
//            .andExpect {
//                status { isOk() }
//            }
//    }
//
//    @Test
//    fun `should get powerplant successfully`() {
//        val mockClient: KredsClient = mock()
//
//        whenever(redisClientFactory.create(any(), any())).thenReturn(mockClient)
//        whenever(mockClient.retrieve(any(), any())).thenReturn("1")
//
//        mockMvc.get("/getPlantById/{plantId}", "Plant1")
//            .andExpect {
//                status { isOk() }
//                jsonPath("$.plantId") { value("Plant1") }
//                jsonPath("$.systemId") { value(1L) }
//            }
//    }
}
