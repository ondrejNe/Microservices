package org.necasond

import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.KredsClientConfig
import io.github.crackthecodeabhi.kreds.connection.newClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeatherApplication

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        runBlocking {
            launch(Dispatchers.Default) {
                val kredClient = newClient(
                    endpoint = Endpoint.from("10.43.167.81:6379"),
                ).apply { auth("WF4tUFtQNi") }

                kredClient.use { client ->
                    client.set("foo", "100")
                    while (true) {
                        val num = client.get("foo")
                        println("foo = $num")
                        delay(5000)
                        client.set("foo", (num + 1))
                    }
                } // <--- the client/connection to redis is closed.
            }
            runApplication<WeatherApplication>(*args)
        }
    }
}
