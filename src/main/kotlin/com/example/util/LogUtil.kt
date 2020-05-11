package com.example.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any> T.logger(): Logger {
    return LoggerFactory.getLogger(this::class.java)
}
