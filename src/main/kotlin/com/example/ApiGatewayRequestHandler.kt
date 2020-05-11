package com.example

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.example.util.logger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class ApiGatewayRequestHandler<I : LambdaRequest, O : ApiGatewayResponse>(modules: List<Module>) : RequestHandler<I, O>,
        KoinComponent {

    private val log = logger()

    init {
        startKoin { modules(modules) }
    }

    override fun handleRequest(request: I, context: Context?): O {
        if (request.isWarmUpRequest()) {
            log.info("Got warmup request")
            ApiGatewayResponse.build {
                statusCode = 200
            }
        }

        log.info("request={}", request)
        return handle(request)
    }

    abstract fun handle(request: I): O

}