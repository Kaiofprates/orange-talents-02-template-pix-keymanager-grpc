package br.com.orange.remove

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class RemoveRequestDto(
        @field:NotBlank
        val key: String,
        @field:Pattern(
                regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$",
                flags = [Pattern.Flag.CASE_INSENSITIVE])
        val clientId: String
)