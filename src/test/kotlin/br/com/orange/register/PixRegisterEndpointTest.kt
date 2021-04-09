package br.com.orange.register

import br.com.orange.*
import br.com.orange.httpClient.InstituicaoResponse
import br.com.orange.httpClient.ItauAccountResponse
import br.com.orange.httpClient.ItauErpClient
import br.com.orange.httpClient.TitularResponse
import br.com.orange.pix.AssocietedAccount
import br.com.orange.pix.PixKey
import br.com.orange.pix.PixRepository
import br.com.orange.validation.KeyType
import io.grpc.ManagedChannelBuilder
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpResponse
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.Assert
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
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

        @JvmStatic
        fun poorlyFormattedPix() = listOf(
                Arguments.of("", KeyType.CPF),
                Arguments.of("569.767.100-86", KeyType.CPF),
                Arguments.of("355.336.50059", KeyType.CPF),
                Arguments.of("569.76710086", KeyType.CPF),
                Arguments.of("", KeyType.PHONE),
                Arguments.of("+5585988714077a", KeyType.PHONE),
                Arguments.of("5585988714077", KeyType.PHONE),
                Arguments.of("", KeyType.MAIL),
                Arguments.of("mailmail.com", KeyType.MAIL),
                Arguments.of("123456789asdf", KeyType.RANDOM),


                )


    }

    @MockBean(ItauErpClient::class)
    fun itauClient(): ItauErpClient? {
        return Mockito.mock(ItauErpClient::class.java)
    }


    @Test
    @DisplayName("Chave gerada com sucesso")
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

    @Test
    @DisplayName("Falha ao cadastrar chave já existente")
    fun `falha ao cadastrar chave já existente`(){

    val pixkey = PixKey(CLIENT_ID,KeyType.CPF,"02467781054",
            Account.CHECKING,
            AssocietedAccount("ITAÚ UNIBANCO S.A.",
                    "Rafael M C Ponte",
                    "02467781054",
            "0001",
            "291900"))
        repository.save(pixkey)

    assertThrows<StatusRuntimeException>{
            SERVICE.register(RegisterRequest.newBuilder()
                    .setAccount(Account.CHECKING)
                    .setClientId(CLIENT_ID.toString())
                    .setValue("02467781054")
                    .setType(Keytype.CPF)
                    .build()) }.also {
                        assertEquals(Status.ALREADY_EXISTS.code,it.status.code)
      }

      val response = SERVICE.removepix(RemoveRequest.newBuilder()
              .setClientId(CLIENT_ID.toString())
              .setKey("02467781054")
              .build()
      ).also {
          assertNotNull(it.message)
      }

    }


    @ParameterizedTest
    @MethodSource("poorlyFormattedPix")
    @DisplayName("Falha na formatação da chave")
    fun `falha no formato da chave`(keyValue: String, keyType: KeyType){

        assertThrows<StatusRuntimeException>{
            SERVICE.register(RegisterRequest.newBuilder()
                    .setAccount(Account.CHECKING)
                    .setClientId(CLIENT_ID.toString())
                    .setValue(keyValue)
                    .setType(Keytype.valueOf(keyType.name))
                    .build()) }.also {
            assertEquals(Status.INVALID_ARGUMENT.code,it.status.code)
        }

    }

    @Test
    @DisplayName("Testa o formato do UUDI")
    fun `uuid teste`(){

        assertThrows<StatusRuntimeException>{
            SERVICE.register(RegisterRequest.newBuilder()
                    .setAccount(Account.CHECKING)
                    .setClientId(CLIENT_ID.toString() + "0")
                    .setValue("02467781054")
                    .setType(Keytype.CPF)
                    .build()) }.also {
            assertEquals(Status.INVALID_ARGUMENT.code,it.status.code)
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


