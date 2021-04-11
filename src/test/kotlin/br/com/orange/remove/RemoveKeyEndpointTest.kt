package br.com.orange.remove

import br.com.orange.Account
import br.com.orange.KeymanagerRemoveServiceGrpc
import br.com.orange.RemoveRequest
import br.com.orange.pix.AssocietedAccount
import br.com.orange.pix.PixKey
import br.com.orange.pix.PixRepository
import br.com.orange.validation.KeyType
import br.com.orange.validation.ValidPixAndClientId
import io.grpc.ManagedChannelBuilder
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.platform.commons.logging.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.validation.ConstraintViolationException

@MicronautTest(transactional = false)
internal class RemoveKeyEndpointTest(@Inject val repository: PixRepository , @Inject val removeKeyService: RemoveKeyService, @Inject val valid: ValidPixAndClientId){


    private val log = LoggerFactory.getLogger(this::class.java)

    @BeforeEach
    fun setup(){
        // limpa o banco de dados para testes
        repository.deleteAll()
        val pixkey = PixKey(CLIENT_ID, KeyType.CPF,"86135457004",
                Account.CHECKING,
                AssocietedAccount("ITAÚ UNIBANCO S.A.",
                        "Yuri Matheus",
                        "86135457004",
                        "0001",
                        "123455"))

        val pixkey2 = PixKey(CLIENT_ID2, KeyType.CPF,"02467781054",
                Account.CHECKING,
                AssocietedAccount("ITAÚ UNIBANCO S.A.",
                        "Rafael M C Ponte",
                        "02467781054",
                        "0001",
                        "291900"))
        pixId2 = repository.save(pixkey2).id!!
        pixId1 =  repository.save(pixkey).id!!
    }

    companion object {
        // cria uma clientId para todo o escopo dos testes
        val CLIENT_ID = UUID.randomUUID()
        val CLIENT_ID2 = UUID.randomUUID()
        val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build()
        val SERVICE = KeymanagerRemoveServiceGrpc.newBlockingStub(channel)

        var pixId1: UUID = UUID.randomUUID()
        var pixId2: UUID = UUID.randomUUID()

    }


    @Test
    @DisplayName("Testa o metodo de busca por chave pix e client id [ and lógico ] ")
    fun `exists by pix and clientId`(){

        /*
        *  teste unitário para o metodo que não permite que um usuário
        *  remova uma chave que não o pertence
        */

        assertTrue(valid.valida( pixId1, CLIENT_ID))
        assertFalse(valid.valida( pixId1, CLIENT_ID2))
        assertTrue(valid.valida( pixId2, CLIENT_ID2))
        assertFalse(valid.valida( pixId2, CLIENT_ID))


    }

    @Test
    @DisplayName("Testa o comportamento do service de remoção de chave pix")
    fun `remove chave`(){

        SERVICE.removepix(RemoveRequest.newBuilder()
                .setClientId(CLIENT_ID.toString())
                .setId(pixId1.toString())
                .build()
        ).also {
            assertEquals(it.message,"Chave removida com sucesso!")
        }

    }

    @Test
    @DisplayName("Testa tentativa de remoção de chave não existente")
    fun `campo mal formatado teste`(){

      assertThrows<StatusRuntimeException> {
            SERVICE.removepix(RemoveRequest.newBuilder()
                    .setClientId(CLIENT_ID.toString())
                    .setId(pixId2.toString())
                    .build())
        }.also {
            assertEquals(Status.NOT_FOUND.code,it.status.code)
        }
    }

    @Test
    @DisplayName("Testa UUID com formatação inválida")
    fun `campo client id  mal formatado teste`(){

      assertThrows<RuntimeException> {
            SERVICE.removepix(RemoveRequest.newBuilder()
                    .setClientId(CLIENT_ID.toString() + 12)
                    .setId("02467781054")
                    .build())
        }.also {
            assertNotNull(it.message)
        }

    }


    @Test
    @DisplayName("Testa tentativa de remoção de chave não existente")
    fun `chave nao existente`(){

      assertThrows<StatusRuntimeException> {
            SERVICE.removepix(RemoveRequest.newBuilder()
                    .setClientId(CLIENT_ID.toString())
                    .setId("0d1bb194-3c52-4e67-8c35-a93c0af9284f")
                    .build())
        }.also {
          assertEquals(Status.NOT_FOUND.code,it.status.code)
        }

    }


    @Test
    @DisplayName("Teste unitário para a formação do UUID")
    fun `remove request to model test`(){

      assertThrows<ConstraintViolationException>{
          removeKeyService.removepix(RemoveRequest.newBuilder()
                  .setClientId(CLIENT_ID.toString() + 12)
                  .setId(pixId1.toString())
                  .build()
                  .toModel())
      }.also {
          assertNotNull(it.message)
      }


    }


}

