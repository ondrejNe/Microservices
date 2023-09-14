package org.necasond.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.KredsClient
import io.github.crackthecodeabhi.kreds.connection.newClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.testcontainers.containers.GenericContainer

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `getForecastTable returns error500 as redis is not running`() {
        runBlocking {
            // Exercise: Perform GET request on /forecast/table
            mockMvc.perform(get("http://localhost:8080/forecast/table"))
                // Verify: Check if the HTTP status is OK and the returned view is "forecast"
                .andExpect(view().name("error500"))
        }
    }
}
