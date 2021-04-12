package br.com.orange.httpClient.bcb

import br.com.orange.httpClient.bcb.request.BcbRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("\${bcb.url}")
interface BancoDoBrasilClient {


    @Post("/api/v1/pix/keys", produces = [MediaType.APPLICATION_XML], consumes = [MediaType.APPLICATION_XML] )
    fun create(@Body request: BcbRequest): HttpResponse<BcbResponse>

    @Delete("/api/v1/pix/keys/{key}",produces = [MediaType.APPLICATION_XML], consumes = [MediaType.APPLICATION_XML])
    fun delete(@Body request: DeleteRequest): HttpResponse<DeleteResponse>

}