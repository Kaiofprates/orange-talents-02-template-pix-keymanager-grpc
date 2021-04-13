package br.com.orange.find

import br.com.orange.Account
import br.com.orange.FindRequest
import br.com.orange.KeymanagerFindKeyGrpc
import br.com.orange.KeymanagerRemoveServiceGrpc
import br.com.orange.pix.AssocietedAccount
import br.com.orange.pix.PixKey
import br.com.orange.pix.PixRepository
import br.com.orange.remove.PixNotExistsException
import br.com.orange.remove.RemoveKeyEndpointTest
import br.com.orange.validation.KeyType
import io.grpc.ManagedChannelBuilder
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.*
import javax.inject.Inject


@MicronautTest(transactional = false)
internal class FindKeyServiceTest(@Inject val repository: PixRepository, ) {


    @BeforeEach
    fun setup(){
        repository.deleteAll()

        val pixkey = PixKey(
            UUID.randomUUID() , KeyType.CPF,"02467781054",
            Account.CHECKING,
            AssocietedAccount("ITAÚ UNIBANCO S.A.",
                "Rafael M C Ponte",
                "02467781054",
                "0001",
                "291900")
        )
        val newPixKey = repository.save(pixkey)
        idTest = newPixKey.id.toString()
        clientIdTest = newPixKey.clientId.toString()
        pixValue = newPixKey.pix
    }


    companion object{
        var idTest: String = ""
        var clientIdTest: String = ""
        var pixValue: String = ""
        val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()
        val SERVICE = KeymanagerFindKeyGrpc.newBlockingStub(channel)

    }


    @Test
    fun `deve retornar erro por chave id com formato inválido`(){

        assertThrows<StatusRuntimeException>{
            SERVICE.findById(FindRequest.newBuilder()
                .setPixkey("")
                .setPixId(
                    FindRequest.FiltroPorPixId.newBuilder()
                        .setId(idTest + 1)
                        .setClientId(clientIdTest)
                        .build())
                .build())
        }.also {
            assertEquals(it.status.code, Status.INVALID_ARGUMENT.code)
        }
    }


    @Test
    fun `deve retornar erro por chave id não existente`(){

        assertThrows<StatusRuntimeException>{
            SERVICE.findById(FindRequest.newBuilder()
                .setPixkey("")
                .setPixId(
                    FindRequest.FiltroPorPixId.newBuilder()
                        .setId(UUID.randomUUID().toString())
                        .setClientId(UUID.randomUUID().toString())
                        .build())
                .build())
        }.also {
            assertEquals(it.status.code, Status.NOT_FOUND.code)
        }
    }

    @Test
    fun `deve retornar erro por pix  inválido`(){

        assertThrows<StatusRuntimeException>{
            SERVICE.findById(FindRequest.newBuilder()
                .setPixkey("")
                .setPixId(
                    FindRequest.FiltroPorPixId.newBuilder()
                        .setId(idTest)
                        .setClientId(clientIdTest)
                        .build())
                .build())
        }.also {
            assertEquals(it.status.code, Status.NOT_FOUND.code)
        }
    }


}