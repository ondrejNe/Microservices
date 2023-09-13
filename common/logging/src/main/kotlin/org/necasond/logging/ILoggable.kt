package org.necasond.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface ILoggable

fun ILoggable.logger(): Logger = logger(this.javaClass)

private fun <T : ILoggable> logger(forClass: Class<T>): Logger =
    loggerForName(forClass.name.removeSuffix("\$Companion"))

private fun loggerForName(forName: String) = LoggerFactory.getLogger(forName)

fun logger(name: String): Logger = loggerForName(name)

inline fun Logger.debug(lazyMessage: () -> String) { if (isDebugEnabled) debug(lazyMessage()) }
inline fun Logger.trace(lazyMessage: () -> String) { if (isTraceEnabled) trace(lazyMessage()) }
inline fun Logger.info(lazyMessage: () -> String) { if (isInfoEnabled) info(lazyMessage()) }
inline fun Logger.warn(lazyMessage: () -> String) { if (isWarnEnabled) warn(lazyMessage()) }
inline fun Logger.error(lazyMessage: () -> String) { if (isErrorEnabled) error(lazyMessage()) }

inline fun Logger.debug(t: Throwable, lazyMessage: () -> String) { if (isDebugEnabled) debug(lazyMessage(), t) }
inline fun Logger.trace(t: Throwable, lazyMessage: () -> String) { if (isTraceEnabled) trace(lazyMessage(), t) }
inline fun Logger.info(t: Throwable, lazyMessage: () -> String) { if (isInfoEnabled) info(lazyMessage(), t) }
inline fun Logger.warn(t: Throwable, lazyMessage: () -> String) { if (isWarnEnabled) warn(lazyMessage(), t) }
inline fun Logger.error(t: Throwable, lazyMessage: () -> String) { if (isErrorEnabled) error(lazyMessage(), t) }
