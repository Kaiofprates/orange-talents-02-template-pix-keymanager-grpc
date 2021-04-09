package br.com.orange.validation

import java.util.*
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Singleton
open class ValidPixAndClientId(var em: EntityManager) {

    @Transactional
    open fun valida(pixId: UUID, clientId: UUID): Boolean{
         val query = em.createQuery("SELECT p FROM PixKey as p WHERE id=:pixId AND clientId =:clientId")
                 .setParameter("pixId", pixId)
                 .setParameter("clientId", clientId)
                 .resultList
         return !query.isEmpty()
     }

}