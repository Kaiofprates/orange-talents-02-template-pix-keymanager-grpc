package br.com.orange.remove

import br.com.orange.pix.PixRepository
import org.slf4j.LoggerFactory
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
open class RemoveKeyService(@Inject val repository: PixRepository) {
    private val log  = LoggerFactory.getLogger(this::class.java)

    @Transactional
    open fun removepix(@Valid requestDto: RemoveRequestDto){

        // REFATORAR - esse metodo deveria ser existisByPixAndClientId

        // cobre a existencia da chave no sistema
        if(!repository.existsByPix(requestDto.key)) throw PixNotExistsException("Chave pix não encontrada")

        // remove a chave do sistema
        repository.deleteByPix(requestDto.key)
    }

}

