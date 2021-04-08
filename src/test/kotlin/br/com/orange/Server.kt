package br.com.orange

import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver

fun main() {
    val server = ServerBuilder.forPort(50051)
            .addService(KeymanagerEndpoint())
            .build()
    server.start()
    server.awaitTermination()
}

class KeymanagerEndpoint: KeymanagerServiceGrpc.KeymanagerServiceImplBase() {
    override fun register(request: RegisterRequest?, responseObserver: StreamObserver<RegisterResponse>?) {

        print(request)

    }
}
