package br.com.orange.list.dto

import br.com.orange.validation.IsUUid
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class RequestDTO (
    @field:IsUUid
    @field:NotBlank
    val clientId: String
)