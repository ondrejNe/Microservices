package org.necasond.model

object ForecastResolver {
    fun resolve(
        value: String,
    ): String {
        val hash = value.hashCode()
        val enumValues = ForecastStates.entries.toTypedArray()
        return enumValues[Math.abs(hash) % enumValues.size].name
    }
}
