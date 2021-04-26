package br.com.orange.shared

import br.com.orange.register.PixKeyExistsException
import br.com.orange.register.PixRegisterEndpoint
import br.com.orange.remove.PixNotExistsException
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import javax.validation.ConstraintViolationException


@InterceptorBean(ErrorHandler::class)
@Singleton
class ExceptionHandlerInterceptor: MethodInterceptor<Any,Any?> {


    val log = LoggerFactory.getLogger(this::class.java)

    override fun intercept(context: MethodInvocationContext<Any, Any?>?): Any? {

        log.info("intercepting method ${context?.targetMethod}")

        try{
            return context?.proceed()
        }catch (t: Throwable){
            log.error("Errorhandler thrown by: ${context?.targetMethod}", t)

            val statusError = when(t){

                is   IllegalArgumentException -> Status.INVALID_ARGUMENT.withDescription(t.message).asRuntimeException()
                is   IllegalStateException -> Status.FAILED_PRECONDITION.withDescription(t.message).asRuntimeException()
                is   PixNotExistsException -> Status.NOT_FOUND.withDescription(t.message).asRuntimeException()
                is   PixKeyExistsException -> Status.ALREADY_EXISTS.withDescription("Falha ao criar novo registro").asRuntimeException()
                is   ConstraintViolationException -> Status.INVALID_ARGUMENT.withDescription(t.message).asRuntimeException()
                else -> Status.UNKNOWN.withDescription("unexpected error happenend").asRuntimeException()

            }

            val responseObserver = context!!.parameterValues[1] as StreamObserver<*>
            responseObserver.onError(statusError)
            return null

        }

    }
}