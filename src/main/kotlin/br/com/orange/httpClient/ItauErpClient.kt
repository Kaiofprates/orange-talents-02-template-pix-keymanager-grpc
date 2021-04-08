package br.com.orange.httpClient

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("\${itau.contas.url}")
interface ItauErpClient {

    @Get("/api/v1/clientes/{clientId}/contas{?tipo}")
    fun getAccount(@PathVariable clientId: String, @QueryValue tipo: String) : HttpResponse<ItauAccountResponse>

    @Get("/api/v1/clientes/c56dfef4-7901-44fb-84e2-a2cefb157890/contas?tipo=CONTA_POUPANCA")
    fun test(): HttpResponse<ItauAccountResponse>

}