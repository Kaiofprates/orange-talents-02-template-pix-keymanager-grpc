package br.com.orange.list

import br.com.orange.Keytype
import br.com.orange.ListResponse
import br.com.orange.list.dto.RequestDTO
import br.com.orange.pix.PixRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
open class ListKeysService {

    @Inject
    lateinit var repository: PixRepository

    @Transactional
    open fun getAll(@Valid request: RequestDTO): ListResponse{

        val allKeys = repository.findAllByClientId(UUID.fromString(request.clientId)).map {
            ListResponse.AllPix.newBuilder()
                .setId(it.id.toString())
                .setKey(Keytype.valueOf(it.type.name))
                .setPix(it.pix)
                .setAccountType(it.accountType)
                .setCreateAt(it.createdAt.toString())
                .build()
        }

        return ListResponse.newBuilder()
            .setClientId(request.clientId)
            .addAllKeys(allKeys)
            .build()

    }

}
