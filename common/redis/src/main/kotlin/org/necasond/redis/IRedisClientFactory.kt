package org.necasond.redis

import io.github.crackthecodeabhi.kreds.connection.KredsClient

interface IRedisClientFactory {
    suspend fun create(
        address: String,
        port: String,
    ): KredsClient
}
