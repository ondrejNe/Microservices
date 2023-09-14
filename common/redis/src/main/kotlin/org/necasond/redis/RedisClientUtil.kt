package org.necasond.redis

import io.github.crackthecodeabhi.kreds.connection.KredsClient

object RedisClientUtil {
    suspend fun KredsClient.upload(
        key: String,
        value: String,
        pass: String,
    ) {
        use { client ->
            client.apply { auth(pass) }
            client.set(key, value)
        }
        close()
    }

    suspend fun KredsClient.retrieve(
        key: String,
        pass: String,
    ): String? {
        var value: String?
        use { client ->
            client.apply { auth(pass) }
            value = client.get(key)
        }
        close()
        return value
    }

    suspend fun KredsClient.delete(
        key: String,
        pass: String,
    ) {
        use { client ->
            client.apply { auth(pass) }
            client.del(key)
        }
        close()
    }

    suspend fun KredsClient.getAll(
        pass: String,
    ): Map<String, String> {
        val value: MutableMap<String, String> = mutableMapOf()
        use { client ->
            client.apply { auth(pass) }
            client.keys("*").forEach { key ->
                value[key] = client.get(key) ?: ""
            }
        }
        close()
        return value.toMap()
    }
}
