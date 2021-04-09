package br.com.orange.handler.custon

import br.com.orange.handler.ExceptionHandler
import br.com.orange.remove.PixNotExistsException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class PixNotExistisExceptionHandler: ExceptionHandler<PixNotExistsException> {

    override fun supports(e: Exception): Boolean {
    return e is PixNotExistsException
    }

    override fun handle(e: PixNotExistsException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(Status.NOT_FOUND
                .withDescription(e.message)
                .withCause(e))
    }
}