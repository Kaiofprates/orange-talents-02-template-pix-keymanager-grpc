package br.com.orange.server

import br.com.orange.KeymanagerServiceGrpc
import br.com.orange.RegisterRequest
import br.com.orange.RegisterResponse
import br.com.orange.validations.KeyValidation
import io.grpc.stub.StreamObserver
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Validated
@Singleton
open class KeyManagerGrpcServer(): KeymanagerServiceGrpc.KeymanagerServiceImplBase() {



    private val log = LoggerFactory.getLogger(this::class.java)

    override fun register(request: RegisterRequest?, responseObserver: StreamObserver<RegisterResponse>?) {

        try{
            var pix = KeyValidation(request!!).valid()
            log.info(pix.toString())
        }catch (e: Exception){
            responseObserver?.onError(e)
        }


    }



}