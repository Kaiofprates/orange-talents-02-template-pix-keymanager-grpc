package br.com.orange.remove

import br.com.orange.pix.PixRepository
import br.com.orange.validation.ValidPixAndClientId
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
open class RemoveKeyService(@Inject val repository: PixRepository, @Inject val existsByPixAndClientId: ValidPixAndClientId) {
    private val log  = LoggerFactory.getLogger(this::class.java)

    @Transactional
    open fun removepix(@Valid requestDto: RemoveRequestDto){


        // cobre a existencia da chave no sistema  e a autenticidade do clientId

        val pixId = UUID.fromString(requestDto.pixId)
        val clientId = UUID.fromString(requestDto.clientId)

        val checkPix = existsByPixAndClientId.valida(pixId = pixId, clientId = clientId)
        if(!checkPix) throw PixNotExistsException("Chave pix não encontrada")

        // remove a chave do sistema
        repository.deleteById(pixId)
    }

}

