package br.com.orange.register

import br.com.orange.Account
import br.com.orange.KeymanagerServiceGrpc
import br.com.orange.Keytype
import br.com.orange.RegisterRequest
import br.com.orange.httpClient.InstituicaoResponse
import br.com.orange.httpClient.ItauAccountResponse
import br.com.orange.httpClient.ItauErpClient
import br.com.orange.httpClient.TitularResponse
import br.com.orange.pix.AssocietedAccount
import br.com.orange.pix.PixRepository
import io.grpc.ManagedChannelBuilder
import io.micronaut.http.HttpResponse
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*
import javax.inject.Inject

@MicronautTest(transactional = false)
internal class PixRegisterEndpointTest(val repository: PixRepository){
    @Inject
    lateinit var itauClient: ItauErpClient

    @BeforeEach
    fun setup(){
        // limpa o banco de dados para testes
        repository.deleteAll()
    }

    companion object {
        // cria uma clientId para todo o escopo dos testes
        val CLIENT_ID = UUID.randomUUID()
        val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build()

        val SERVICE = KeymanagerServiceGrpc.newBlockingStub(channel)

    }

    @MockBean(ItauErpClient::class)
    fun itauClient(): ItauErpClient? {
        return Mockito.mock(ItauErpClient::class.java)
    }


    @Test
    fun `sucesso ao gerar pix`(){
    `when`(itauClient.getAccount(clientId = CLIENT_ID.toString(), tipo = "CONTA_CORRENTE"))
            .thenReturn(HttpResponse.ok(ItauAccountResponse()))

     val response  = SERVICE.register(RegisterRequest.newBuilder()
             .setAccount(Account.CHECKING)
             .setClientId(CLIENT_ID.toString())
             .setValue("rponte@zup.com.br")
             .setType(Keytype.MAIL)
             .build())

      with(response){
          assertEquals(CLIENT_ID.toString(),clientId)
          assertNotNull(id)
      }

    }

    fun ItauAccountResponse(): ItauAccountResponse {
        return br.com.orange.httpClient.ItauAccountResponse(
                "CONTA_CORRENTE",
                InstituicaoResponse("UNIBANCO ITAU SA", AssocietedAccount.ITAU_UNIBANCO_ISPB),
                "0001",
                "291900",
                TitularResponse("Rafael M C Ponte","02467781054")
        )
    }


}


