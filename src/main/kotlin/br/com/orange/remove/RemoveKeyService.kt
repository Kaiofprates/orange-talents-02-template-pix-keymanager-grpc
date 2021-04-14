package br.com.orange.remove

import br.com.orange.httpClient.bcb.BancoDoBrasilClient
import br.com.orange.httpClient.bcb.DeleteRequest
import br.com.orange.pix.PixRepository
import br.com.orange.validation.ValidPixAndClientId
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpStatus
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
open class RemoveKeyService(
    @Inject val repository: PixRepository,
    @Inject val existsByPixAndClientId: ValidPixAndClientId,
    @Inject val bcClient: BancoDoBrasilClient
) {

    @Transactional
    open fun removepix(@Valid requestDto: RemoveRequestDto){


        // cobre a existencia da chave no sistema  e a autenticidade do clientId

        val pixId = UUID.fromString(requestDto.pixId)
        val clientId = UUID.fromString(requestDto.clientId)

        val checkPix = existsByPixAndClientId.valida(pixId = pixId, clientId = clientId)
        if(!checkPix) throw PixNotExistsException("Chave pix não encontrada")

        val pix = repository.findById(pixId)

        val deleteRequest = DeleteRequest(pix.get().pix,"60701190")
        val response  = bcClient.delete(deleteRequest)

        if(response.status != HttpStatus.OK) throw StatusRuntimeException(Status.UNAVAILABLE)

        // remove a chave do sistema
        repository.deleteById(pixId)




    }

}

