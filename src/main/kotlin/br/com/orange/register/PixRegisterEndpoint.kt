package br.com.orange.register

import br.com.orange.KeymanagerServiceGrpc
import br.com.orange.RegisterRequest
import br.com.orange.RegisterResponse
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixRegisterEndpoint(        @Inject private val service: NewPixService
): KeymanagerServiceGrpc.KeymanagerServiceImplBase() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun register(request: RegisterRequest?, responseObserver: StreamObserver<RegisterResponse>?) {

        val newPix = request?.toModel()
        log.info(newPix.toString())
        service.register(newPix!!)

        responseObserver?.onNext(RegisterResponse.newBuilder()
                .setId(12)
                .setClientId("12312313")
                .build())
        responseObserver?.onCompleted()


    }

}