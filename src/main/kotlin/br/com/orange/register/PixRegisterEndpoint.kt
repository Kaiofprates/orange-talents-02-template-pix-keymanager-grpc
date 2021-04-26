package br.com.orange.register

import br.com.orange.KeymanagerServiceGrpc
import br.com.orange.RegisterRequest
import br.com.orange.RegisterResponse
import br.com.orange.shared.ErrorHandler
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class PixRegisterEndpoint(        @Inject private val service: NewPixService
): KeymanagerServiceGrpc.KeymanagerServiceImplBase() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun register(request: RegisterRequest?, responseObserver: StreamObserver<RegisterResponse>?) {

        val newPix = request?.toModel()
        log.info(newPix.toString())
         val pix =  service.register(newPix!!)

            responseObserver?.onNext(RegisterResponse.newBuilder()
                    .setId(pix.id.toString())
                    .setClientId(pix.clientId.toString())
                    .build())
            responseObserver?.onCompleted()

            // TODO: 09/04/2021  substuir por um errorhandler 


    }

}