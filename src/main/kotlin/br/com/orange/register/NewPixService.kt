package br.com.orange.register

import br.com.orange.Account
import br.com.orange.httpClient.ItauErpClient
import br.com.orange.pix.PixRepository
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NewPixService(@Inject val repository: PixRepository,@Inject val itauClient: ItauErpClient) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun register(@Valid newPixKey: NewPixKey): NewPixKey {
        // 1. Verifica a existência da chave no sistema
        if(repository.existsByPix(newPixKey.pix)) throw PixKeyExistsException("Falha ao registrar nova chave")
        // 2. Busca dados junto ao sistema do ITAU
        val accountType = when(newPixKey.account){
            Account.SAVINGS -> "CONTA_POUPANCA"
            Account.CHECKING -> "CONTA_CORRENTE"
            else -> null
        }
        val response  = itauClient.getAccount(newPixKey.clientId!!,accountType!!)
        //val response  = itauClient.test()
        val account = response.body()?.toModel() ?: throw IllegalStateException("Cliente não encontrado")
        log.info(" account type: $accountType  -- client id: ${newPixKey.clientId}")
        log.info(account.toString())
        // 3 - Persistindo no banco de dados
        val pix = newPixKey.toModel(account)
        repository.save(pix)

        return newPixKey
    }


}
