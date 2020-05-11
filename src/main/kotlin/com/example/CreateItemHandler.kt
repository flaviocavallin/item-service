package com.example

import com.example.di.prodModule
import com.example.domain.Item
import com.example.dto.ItemDTO
import com.example.repository.ItemRepository
import com.example.util.logger
import com.fasterxml.jackson.databind.ObjectMapper
import org.koin.core.inject
import org.koin.core.module.Module
import org.slf4j.Logger

class CreateItemHandler(modules: List<Module> = listOf(prodModule)) : ApiGatewayRequestHandler<LambdaRequest, ApiGatewayResponse>(modules) {
    private val objectMapper: ObjectMapper by inject()
    private val itemRepository: ItemRepository by inject()

    companion object {
        private val log: Logger = logger()
    }

    override fun handle(request: LambdaRequest): ApiGatewayResponse {
        val event = objectMapper.readValue(request.body, ItemDTO::class.java)
        log.info("event={}", event)

        val item = Item(event.name, event.description)

        return try {
            itemRepository.save(item)
            ApiGatewayResponse.build {
                statusCode = 200
                objectBody = ItemDTO(item.id, item.name, item.description)
            }
        } catch (e: Exception) {
            log.error("There was unexpected error:", e)

            ApiGatewayResponse.build {
                statusCode = 500
            }
        }
    }
}
