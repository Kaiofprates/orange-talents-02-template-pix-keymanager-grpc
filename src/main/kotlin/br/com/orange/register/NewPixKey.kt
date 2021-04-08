package br.com.orange.register

import br.com.orange.Account
import br.com.orange.Keytype
import br.com.orange.pix.AssocietedAccount
import br.com.orange.pix.PixKey
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
data class NewPixKey(
        @field:NotBlank
        val clientId: String?,
        @field:NotNull
        val type: Keytype?,
        @field:Size(max = 77)
        val pix: String?,
        @field:NotNull
        val account: Account?
) {
        fun toModel(account: AssocietedAccount): PixKey {


                return PixKey(
                        clientId = UUID.fromString(this.clientId),
                        type = Keytype.valueOf(this.type!!.name),
                        pix = if(this.type == Keytype.RANDOM) UUID.randomUUID().toString() else this.pix!!,
                        accountType = Account.valueOf(this.account!!.name),
                        account = account
                )

        }

}
