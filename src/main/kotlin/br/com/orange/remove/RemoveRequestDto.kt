package br.com.orange.remove

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class RemoveRequestDto(

        // refatorar essa lógica para uma annotation de validação de pattern

        @field:NotBlank
        @field:Pattern(
                regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$",
                flags = [Pattern.Flag.CASE_INSENSITIVE], message = "Formato de id inválido")
        val pixId: String,
        @field:NotBlank
        @field:Pattern(
                regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$",
                flags = [Pattern.Flag.CASE_INSENSITIVE], message = "Formato de id inválido")
        val clientId: String
)