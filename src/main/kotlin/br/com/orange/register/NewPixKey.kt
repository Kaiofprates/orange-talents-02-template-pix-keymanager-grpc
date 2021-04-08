package br.com.orange.register

import br.com.orange.Account
import br.com.orange.pix.AssocietedAccount
import br.com.orange.pix.PixKey
import br.com.orange.validation.KeyType
import br.com.orange.validation.ValidPixKey
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
data class NewPixKey(
        @field:NotBlank
        @field:Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$",
                flags = [Pattern.Flag.CASE_INSENSITIVE])
        val clientId: String?,
        @field:NotNull
        val type: KeyType?,
        @field:Size(max = 77)
        val pix: String?,
        @field:NotNull
        val account: Account?
) {
        fun toModel(account: AssocietedAccount): PixKey {


                return PixKey(
                        clientId = UUID.fromString(this.clientId),
                        type = KeyType.valueOf(this.type!!.name),
                        pix = if(this.type == KeyType.RANDOM) UUID.randomUUID().toString() else this.pix!!,
                        accountType = Account.valueOf(this.account!!.name),
                        account = account
                )

        }

}
