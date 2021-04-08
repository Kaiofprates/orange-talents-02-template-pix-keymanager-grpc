package br.com.orange.pix

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
open class AssocietedAccount(
        @field:NotBlank val instituicao: String,
        @field:NotBlank val nomeDotitular: String,
        @field:NotBlank val cpfDoTitular: String,
        @field:NotBlank val agencia: String,
        @field:NotBlank val numeroDaConta: String

) {
}
