package com.example

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent

open class LambdaRequest {
    var pathParameters: Map<String, String>? = null
    var httpMethod: String? = null
    var requestContext: APIGatewayProxyRequestEvent.ProxyRequestContext? = null
    var source: String? = null
    var body: String? = ""
    var query: Map<String, String>? = null
    var queryStringParameters: Map<String, String>? = null

    fun getRequestId(): String? {
        return requestContext?.requestId
    }

    fun isWarmUpRequest(): Boolean {
        return source?.contains("warmup") == true
    }

    fun pathVariable(key: String): String? {
        return pathParameters?.get(key)
    }

    fun queryParameter(name: String): String? {
        return queryStringParameters?.get(name)
    }

    override fun toString(): String {
        return "LambdaRequest(pathParameters=$pathParameters, httpMethod=$httpMethod, requestContext=$requestContext, " +
                "source=$source, body=$body, query=$query, queryStringParameters=$queryStringParameters)"
    }
}