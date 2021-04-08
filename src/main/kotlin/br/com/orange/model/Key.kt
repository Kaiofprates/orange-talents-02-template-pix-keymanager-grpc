package br.com.orange.model

import br.com.orange.Account
import br.com.orange.Keytype
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "keys")
data class Key(
        @field:NotBlank(message = "O campo não deve ser nulo ou em branco")
        val clientId: String,

        @Column(unique = true)
        val pix: String,

        @Enumerated(EnumType.STRING)
        @field:NotNull
        val keyType: Keytype,

        @Enumerated(EnumType.STRING)
        @field:NotNull
        val account: Account

        ){
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
