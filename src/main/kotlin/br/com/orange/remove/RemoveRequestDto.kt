package br.com.orange.remove

import br.com.orange.validation.IsUUid
import javax.validation.constraints.NotBlank

class RemoveRequestDto(

        @field:NotBlank
        @field:IsUUid
        val pixId: String,
        @field:NotBlank
        @field:IsUUid
        val clientId: String
)