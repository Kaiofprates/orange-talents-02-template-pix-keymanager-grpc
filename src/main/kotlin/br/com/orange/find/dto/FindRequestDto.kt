package br.com.orange.find.dto

import br.com.orange.validation.IsUUid
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class FindRequestDto (
    @field:NotBlank
    @field:IsUUid
    val id: String,
    @field:NotBlank
    @field:IsUUid
    val clientId: String
    ){
}
