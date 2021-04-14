package br.com.orange.list

import br.com.orange.KeymanagerListServiceGrpc
import br.com.orange.ListRequest
import br.com.orange.ListResponse
import br.com.orange.list.dto.RequestDTO
import io.grpc.ManagedChannelBuilder
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*
import javax.inject.Inject


@MicronautTest
internal class ListKeysEndPointTest{

    @Inject
    lateinit var listService: ListKeysService


    companion object {
        val channel  = ManagedChannelBuilder.forAddress("localhost",50051)
            .usePlaintext()
            .build()
        val SERVER = KeymanagerListServiceGrpc.newBlockingStub(channel)
        val CLIENTID = UUID.randomUUID().toString()
    }


    @MockBean(ListKeysService::class)
    fun listService(): ListKeysService? {
        return mock(ListKeysService::class.java)
    }

    @Test
    fun `deve retornar uma lista vazia`(){

        `when`(listService.getAll(RequestDTO(CLIENTID)))
            .thenReturn(listResponseTest())

        val response = SERVER.listKey(
            ListRequest.newBuilder()
                .setClientId(CLIENTID)
                .build()
        )

        with(response){
            assertEquals(response.clientId, CLIENTID)
            assertTrue(response.keysList.isEmpty())
        }


    }


    fun listResponseTest(): ListResponse {
        return  ListResponse.newBuilder()
            .addAllKeys(arrayListOf())
            .setClientId(CLIENTID)
            .build()
    }


}