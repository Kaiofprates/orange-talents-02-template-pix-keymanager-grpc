package br.com.orange.find

import br.com.orange.Account
import br.com.orange.KeymanagerFindKeyGrpc
import br.com.orange.KeymanagerRemoveServiceGrpc
import br.com.orange.pix.AssocietedAccount
import br.com.orange.pix.PixKey
import br.com.orange.pix.PixRepository
import br.com.orange.remove.RemoveKeyEndpointTest
import br.com.orange.validation.KeyType
import io.grpc.ManagedChannelBuilder
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import javax.inject.Inject


@MicronautTest(transactional = false)
internal class FindKeyServiceTest(@Inject val repository: PixRepository, ) {


    @BeforeEach
    fun setup(){
        repository.deleteAll()

        val pixkey = PixKey(
            RemoveKeyEndpointTest.CLIENT_ID2, KeyType.CPF,"02467781054",
            Account.CHECKING,
            AssocietedAccount("ITAÚ UNIBANCO S.A.",
                "Rafael M C Ponte",
                "02467781054",
                "0001",
                "291900")
        )
        val newPixKey = repository.save(pixkey)
        clientIdTest = newPixKey.id.toString()
        idTest = newPixKey.clientId.toString()
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
    fun `



}