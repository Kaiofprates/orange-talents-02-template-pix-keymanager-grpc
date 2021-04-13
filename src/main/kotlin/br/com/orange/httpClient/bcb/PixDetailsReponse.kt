package br.com.orange.httpClient.bcb

import io.micronaut.core.annotation.Introspected
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Introspected
data class PixDetailsReponse(
    val keyType: String,
    val key: String,
    @Embedded
    val bankAccount: BankAccount,
    @Embedded
    val owner: Owner,
    val createdAt: String

)

@Embeddable
data class Owner (
    val type: String,
    val name: String,
    val taxIdNumber: String
    )

@Embeddable
data class BankAccount(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: String
)
