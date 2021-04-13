package br.com.orange.find

import br.com.orange.httpClient.bcb.BancoDoBrasilClient
import br.com.orange.pix.PixKey
import br.com.orange.pix.PixRepository
import br.com.orange.remove.PixNotExistsException
import io.micronaut.http.HttpStatus
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class FindKeyService(
    @Inject val repository: PixRepository,
    @Inject val bcbclient: BancoDoBrasilClient
) {

    @Transactional
    open fun getPix(key: String): PixKey{

// TODO: 12/04/2021  garantir que a chave exista no banco central e tambem no banco de dados da aplicação

        val response = bcbclient.findById(key)
        if(response.status == HttpStatus.NOT_FOUND) throw PixNotExistsException("Chave não encontrada")

        val pix = repository.findById(UUID.fromString(key))

        return pix.get()
    }


}