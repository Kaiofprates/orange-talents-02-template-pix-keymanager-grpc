package br.com.orange.pix

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface PixRepository: JpaRepository<PixKey,UUID> {
   fun existsByPix(pix: String?): Boolean
   fun findByClientId(clientId: UUID?): Optional<PixKey>
   fun findByPix(pix: String): Optional<PixKey>
   fun findAllByClientId(fromString: UUID?): List<PixKey>
}