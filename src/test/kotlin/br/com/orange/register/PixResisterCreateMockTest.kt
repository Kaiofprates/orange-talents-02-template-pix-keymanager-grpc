package br.com.orange.register

import br.com.orange.Account
import br.com.orange.Keytype
import br.com.orange.RegisterRequest
import br.com.orange.httpClient.InstituicaoResponse
import br.com.orange.httpClient.ItauAccountResponse
import br.com.orange.httpClient.TitularResponse
import br.com.orange.httpClient.bcb.*
import br.com.orange.httpClient.bcb.request.BankAccountRequest
import br.com.orange.httpClient.bcb.request.BcbRequest
import br.com.orange.httpClient.bcb.request.OwnerRequest
import br.com.orange.httpClient.itau.ItauErpClient
import br.com.orange.pix.AssocietedAccount
import br.com.orange.validation.KeyType
import io.micronaut.http.HttpResponse
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest
class PixResisterCreateMockTest {

    @Inject
    lateinit var itauClient: ItauErpClient

    @Inject
    lateinit var bcbClient: BancoDoBrasilClient


    @MockBean(ItauErpClient::class)
    fun itauClient(): ItauErpClient? {
        return Mockito.mock(ItauErpClient::class.java)
    }

    @MockBean(BancoDoBrasilClient::class)
    fun bcbClient(): BancoDoBrasilClient?{
        return Mockito.mock(BancoDoBrasilClient::class.java)
    }


    @Test
    @DisplayName("Chave gerada com sucesso")
    fun `sucesso ao gerar pix`(){
        Mockito.`when`(
            itauClient.getAccount(
                clientId = PixRegisterEndpointTest.CLIENT_ID.toString(),
                tipo = "CONTA_CORRENTE"
            )
        )
            .thenReturn(HttpResponse.ok(ItauAccountResponse()))
        Mockito.`when`(bcbClient.create(bcbRequest()))
            .thenReturn(HttpResponse.created(bcbResponse()))

        val response  = PixRegisterEndpointTest.SERVICE.register(
            RegisterRequest.newBuilder()
            .setAccount(Account.CHECKING)
            .setClientId(PixRegisterEndpointTest.CLIENT_ID.toString())
            .setValue("02467781054")
            .setType(Keytype.CPF)
            .build())

        with(response){
            Assertions.assertEquals(PixRegisterEndpointTest.CLIENT_ID.toString(), clientId)
            Assertions.assertNotNull(id)
        }
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

fun bcbRequest(): BcbRequest {
    return BcbRequest(
        keyType = KeyType.CPF,
        key = "02467781054",
        bankAccount = BankAccountRequest(
            participant = "60701190",
            branch = "0001",
            accountNumber = "291900",
            accountType = AccountType.CACC,
        ),
        owner = OwnerRequest(
            type = PersonType.NATURAL_PERSON,
            name = "Rafael M C Ponte",
            taxIdNumber = "02467781054"
        )
    )
}

fun bcbResponse(): BcbResponse {
    return BcbResponse(
        key = "02467781054",
        keytype = "CPF",
        bankAccount = BankAccountResponse(
            participant = "60701190",
            branch = "0001",
            accountNumber = "291900",
            accountType = AccountType.CACC,
        ),
        owner = OwnerResponse(
            type = PersonType.NATURAL_PERSON
        ),
        createdAt = "2021-04-14T12:57:13.163953",
        name = "Rafael M C Ponte",
        taxIdNumber = "02467781054"
    )
}