package br.com.orange.list

import br.com.orange.KeymanagerListServiceGrpc
import br.com.orange.ListRequest
import io.grpc.ManagedChannelBuilder
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*

@MicronautTest
class ListKeysTest {


    companion object {
        val channel  = ManagedChannelBuilder.forAddress("localhost",50051)
            .usePlaintext()
            .build()
        val SERVER = KeymanagerListServiceGrpc.newBlockingStub(channel)

        @JvmStatic
        fun poorlyFormattedClientId() = listOf(
            Arguments.of(UUID.randomUUID().toString() + "a"),
            Arguments.of(""),
            Arguments.of("sei la")
        )

    }

    @ParameterizedTest
    @MethodSource("poorlyFormattedClientId")
    fun `deve retornar InvalidArgument`(clientid: String){

        assertThrows<StatusRuntimeException> {
            SERVER.listKey(
                ListRequest.newBuilder()
                    .setClientId(clientid)
                    .build()
            )
        }.also {
            Assertions.assertEquals(it.status.code, Status.INVALID_ARGUMENT.code)
        }

    }



}