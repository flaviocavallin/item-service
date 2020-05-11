package com.example

import org.testcontainers.containers.GenericContainer

class DynamoDBContainer : GenericContainer<DynamoDBContainer>("amazon/dynamodb-local:latest") {
    private val log = logger()

    private val connectionPort = 8000

    init {
        withExposedPorts(connectionPort)
    }

    fun connectionPort(): Int {
        val port = getMappedPort(connectionPort)
        log.info("DynamoDB exposed on port={}", port)
        return port
    }
}