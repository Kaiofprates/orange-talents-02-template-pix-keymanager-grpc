package br.com.orange.pix

import br.com.orange.Account
import br.com.orange.validation.KeyType
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "pixkeys")
data class PixKey(
        val clientId: UUID,
        @field:NotNull @Enumerated(EnumType.STRING) val type: KeyType,
        @field:NotBlank @Column(unique = true ) var pix: String,
        @field:NotNull @Enumerated(EnumType.STRING) val accountType: Account,
        @field:NotNull @field:Valid @Embedded val account: AssocietedAccount
) {
    @Id @GeneratedValue
    val id: UUID? = null
    val createdAt = LocalDateTime.now()

    fun isRandom(): Boolean{
        return  type == KeyType.RANDOM
    }

}