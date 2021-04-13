package br.com.orange.find

import br.com.orange.FindResponse
import br.com.orange.find.dto.FindRequestDto
import br.com.orange.find.dto.PixKeyDto
import br.com.orange.find.extensions.toResponse
import br.com.orange.httpClient.bcb.BancoDoBrasilClient
import br.com.orange.pix.PixRepository
import br.com.orange.remove.PixNotExistsException
import io.micronaut.http.HttpStatus
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
open class FindKeyService(
    @Inject val repository: PixRepository,
    @Inject val bcbclient: BancoDoBrasilClient
) {


    @Transactional
    open fun getPix(@Valid requestDto: FindRequestDto): FindResponse{

        val pix = repository.findById(UUID.fromString(requestDto.id))

        if(pix.isEmpty || pix.get().clientId != UUID.fromString(requestDto.clientId)) {
            throw PixNotExistsException("Chave não encontrada")
        }

        val response = bcbclient.findById(pix.get().pix)
        if(response.status == HttpStatus.NOT_FOUND) throw PixNotExistsException("Chave não encontrada")

        return pix.get().toResponse()
    }

    @Transactional
    open fun getPix(@Valid pix: PixKeyDto): FindResponse {

       // pesquisa inicial no banco de dados da aplicação

      val localresponse = repository.findByPix(pix.pixkey)
      if(localresponse.isPresent){
          return localresponse.get().toResponse()
      }

      // pesquisa alternativa no banco Central

      val response = bcbclient.findById(pix.pixkey)
      if(response.status == HttpStatus.OK){
          return response.body().toResponse()
      }else{
          throw PixNotExistsException("Chave não encontrada")
      }

    }



}