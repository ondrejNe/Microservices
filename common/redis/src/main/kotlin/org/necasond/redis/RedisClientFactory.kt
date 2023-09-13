package org.necasond.redis

import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.KredsClient
import io.github.crackthecodeabhi.kreds.connection.newClient

object RedisClientFactory : IRedisClientFactory {
    override suspend fun create(
        address: String,
        port: String,
    ): KredsClient = newClient(
        endpoint = Endpoint.from("$address:$port"),
    )
}
