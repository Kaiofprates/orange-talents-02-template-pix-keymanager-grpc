package br.com.orange.validation

import java.util.*
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Singleton
open class ValidPixAndClientId(var em: EntityManager) {

    // FIXME: 09/04/2021 substituir por uma query no repository, essa lógica só existiu por um erro no banco de dados,
    // Criei o banco com clientId como string e depois mudei seu campo no código mas o banco persistiu

    @Transactional
    open fun valida(pixId: UUID, clientId: UUID): Boolean{
         val query = em.createQuery("SELECT p FROM PixKey as p WHERE id=:pixId AND clientId =:clientId")
                 .setParameter("pixId", pixId)
                 .setParameter("clientId", clientId)
                 .resultList
         return !query.isEmpty()
     }

}