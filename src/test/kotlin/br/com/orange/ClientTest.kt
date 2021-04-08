package br.com.orange

import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.Test

@Test
fun `send request test`() {

    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()

    val request = RegisterRequest.newBuilder()
            .setAccount(Account.CHECKING)
            .setType(Keytype.CPF)
            .setKey("10335100686")
            .build()

    val client = KeymanagerServiceGrpc.newBlockingStub(channel)
    val response = client.register(request)
    println(response)

}