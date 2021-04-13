package br.com.orange.find.dto

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Size

@Introspected
data class PixKeyDto(
    @field:Size( max = 77)
    val pixkey: String
)