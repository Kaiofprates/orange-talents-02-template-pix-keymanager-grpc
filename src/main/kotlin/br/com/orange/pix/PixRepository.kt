package br.com.orange.pix

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface PixRepository: JpaRepository<PixKey,UUID> {
   fun existsByPix(pix: String?): Boolean
   fun deleteByPix(pix: String)
   @Query("SELECT p FROM PixKey as p WHERE pix=:key AND clientId =:clientId")
   fun existsByPixAndClientId(key: String, clientId: String): Optional<PixKey>
   fun findByClientId(clientId: UUID?): Optional<PixKey>
}