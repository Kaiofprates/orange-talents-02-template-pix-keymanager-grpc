package br.com.orange.register

import br.com.orange.KeymanagerServiceGrpc
import br.com.orange.RegisterRequest
import br.com.orange.RegisterResponse
import io.grpc.Status
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class PixRegisterEndpoint(        @Inject private val service: NewPixService
): KeymanagerServiceGrpc.KeymanagerServiceImplBase() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun register(request: RegisterRequest?, responseObserver: StreamObserver<RegisterResponse>?) {

        val newPix = request?.toModel()
        log.info(newPix.toString())

        try{
           val pix =  service.register(newPix!!)

            responseObserver?.onNext(RegisterResponse.newBuilder()
                    .setId(pix.id.toString())
                    .setClientId(pix.clientId.toString())
                    .build())
            responseObserver?.onCompleted()

        }catch (error: PixKeyExistsException){

            responseObserver?.onError(Status.ALREADY_EXISTS
                    .withDescription("Falha ao criar novo registro")
                    .augmentDescription("A chave indicada já consta no banco de dados")
                    .asRuntimeException())

        }catch (error: ConstraintViolationException){
            responseObserver?.onError(Status.INVALID_ARGUMENT
                    .withDescription(error.message)
                    .asRuntimeException())

        }catch (error: IllegalStateException){
            responseObserver?.onError( Status.NOT_FOUND
                    .withDescription(error.message)
                    .asRuntimeException())
        }


    }

}