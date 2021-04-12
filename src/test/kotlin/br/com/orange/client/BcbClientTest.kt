package br.com.orange.client

import br.com.orange.httpClient.bcb.*
import br.com.orange.httpClient.bcb.request.BankAccountRequest
import br.com.orange.httpClient.bcb.request.BcbRequest
import br.com.orange.httpClient.bcb.request.OwnerRequest
import br.com.orange.validation.KeyType
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.badRequest
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import javax.inject.Inject

@MicronautTest
class BcbClientTest(@Inject val client: BancoDoBrasilClient) {


    @MockBean(BancoDoBrasilClient::class)
    fun client(): BancoDoBrasilClient? {
        return mock(BancoDoBrasilClient::class.java)
    }


    @Test
    @DisplayName("Deve testar comportamento da rota de create")
    fun create(){

        val newpix = BcbRequest(bankAccount = BankAccountRequest(
            participant = "60701190",
            branch = "0001",
            accountType = AccountType.SVGS,
            accountNumber = "123"
        ),key = "12345",owner = OwnerRequest(
            type = PersonType.NATURAL_PERSON,
            taxIdNumber = "123123",
            name = "John Doe"
        ),keyType = KeyType.CPF)

        `when`(client.create(newpix))
            .thenReturn(ok())

        val response = client.create(newpix)

        assertTrue(response.status == HttpStatus.OK)

    }


    @Test
    @DisplayName("Testa a falha na rota create")
    fun `testa falha`(){

        val newpix = BcbRequest(bankAccount = BankAccountRequest(
            participant = "60701190",
            branch = "0001",
            accountType = AccountType.SVGS,
            accountNumber = "123"
        ),key = "12345",owner = OwnerRequest(
            type = PersonType.NATURAL_PERSON,
            taxIdNumber = "123123",
            name = "John Doe"
        ),keyType = KeyType.CPF)

        `when`(client.create(newpix))
            .thenReturn(badRequest())
        val response = client.create(newpix)

        assertTrue(response.status == HttpStatus.BAD_REQUEST)

    }


    @Test
    @DisplayName("Testa a rota de delete com sucesso")
    fun delete(){
        val request = DeleteRequest("1231231","123123123")
        `when`(client.delete(request)).thenReturn(ok())

        val response = client.delete(request)

        assertEquals(response.status, HttpStatus.OK)

    }


}