package com.example

import com.example.di.prodModule
import com.example.domain.Item
import com.example.dto.ItemDTO
import com.example.repository.ItemRepository
import com.example.util.logger
import org.koin.core.inject
import org.koin.core.module.Module

class GetItemHandler(modules: List<Module> = listOf(prodModule)) : ApiGatewayRequestHandler<LambdaRequest, ApiGatewayResponse>(modules) {
    private val itemRepository: ItemRepository by inject()

    companion object {
        private val log = logger()
    }

    override fun handle(request: LambdaRequest): ApiGatewayResponse {
        log.info("request={}", request)

        val id = getId(request) ?: return errorResponse()

        val item: Item? = itemRepository.getItem(id)

        return if (item != null) {
            ApiGatewayResponse.build {
                statusCode = 200
                objectBody = ItemDTO(item.id, item.name, item.description)
            }
        } else {
            ApiGatewayResponse.build {
                statusCode = 404
            }
        }
    }

    private fun getId(request: LambdaRequest): String? {
        val id = request.pathVariable("id")
        if (id.isNullOrBlank()) {
            log.info("Request will be discarded because id is blank. request=$request")
            return null
        }
        return id
    }

    private fun errorResponse(): ApiGatewayResponse {
        return ApiGatewayResponse.build {
            statusCode = 404
        }
    }
}
