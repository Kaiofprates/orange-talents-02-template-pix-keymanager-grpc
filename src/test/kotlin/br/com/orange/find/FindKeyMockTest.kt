package br.com.orange.find

import br.com.orange.Account
import br.com.orange.FindRequest
import br.com.orange.KeymanagerFindKeyGrpc
import br.com.orange.find.dto.PixKeyDto
import br.com.orange.find.extensions.toResponse
import br.com.orange.httpClient.bcb.BancoDoBrasilClient
import br.com.orange.httpClient.bcb.BankAccount
import br.com.orange.httpClient.bcb.Owner
import br.com.orange.httpClient.bcb.PixDetailsReponse
import br.com.orange.pix.AssocietedAccount
import br.com.orange.pix.PixKey
import br.com.orange.pix.PixRepository
import br.com.orange.validation.KeyType
import io.grpc.ManagedChannelBuilder
import io.micronaut.http.HttpResponse
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject


@MicronautTest(transactional = false)
internal class FindKeyMockTest(
    @Inject val repository: PixRepository,
    @Inject val bancoDoBrasilClient: BancoDoBrasilClient,
    @Inject val findKeyService: FindKeyService) {


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

    @MockBean(BancoDoBrasilClient::class)
    fun bancoDoBrasilClient(): BancoDoBrasilClient? {
        return Mockito.mock(BancoDoBrasilClient::class.java)
    }

    @MockBean(FindKeyService::class)
    fun findKeyService(): FindKeyService? {
        return Mockito.mock(FindKeyService::class.java)
    }

    @Test
    fun `deve retornar sucesso ao retornar chave`(){

        val testpix = repository.findByPix(pixValue)

        Mockito.`when`(findKeyService.getPix(PixKeyDto(pixValue)))
            .thenReturn(testpix.get().toResponse())

        Mockito.`when`(bancoDoBrasilClient.findById(pixValue))
            .thenReturn(HttpResponse.ok(pixDetails()))

        val reponse = SERVICE.findById(
            FindRequest.newBuilder()
            .setPixkey(FindKeyServiceTest.pixValue)
            .build())

        with(reponse){
            Assertions.assertEquals(reponse.clientId, clientId)
        }
    }


    fun pixDetails(): PixDetailsReponse {
        return PixDetailsReponse(
            keyType = "RANDOM",
            key = "40f39bd6-6cc1-46be-9d15-2a7adda76ce2",
            createdAt = "2021-04-13T19:04:20.601449",
            bankAccount = BankAccount(
                participant = "ITAÚ UNIBANCO S.A.",
                branch = "0001",
                accountNumber = "212233",
                accountType = "CHECKING"
            ),
            owner = Owner(
                type = "",
                name = "Alberto Tavares",
                taxIdNumber = "06628726061"
            )
        )
    }



}