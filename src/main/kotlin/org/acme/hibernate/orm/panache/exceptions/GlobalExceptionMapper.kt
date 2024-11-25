package org.acme.hibernate.orm.panache.exception

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class GlobalExceptionMapper : ExceptionMapper<Exception> {

    override fun toResponse(exception: Exception): Response {
        val status = when (exception) {
            is IllegalArgumentException -> Response.Status.BAD_REQUEST // 400
            is IllegalStateException -> Response.Status.CONFLICT // 409
            else -> Response.Status.INTERNAL_SERVER_ERROR // 500
        }

        val errorResponse = mapOf(
            "message" to exception.message,
            "type" to exception::class.simpleName,
            "status" to status.statusCode
        )

        return Response.status(status).entity(errorResponse).build()
    }
}
