package necasond.uno

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        println("Hello, world! from ${Main::class.java.name}")
        runBlocking {
            while (true) {
                println("Heartbeat from ${Main::class.java.name}")
                delay(2000L)
            }
        }
    }
}
