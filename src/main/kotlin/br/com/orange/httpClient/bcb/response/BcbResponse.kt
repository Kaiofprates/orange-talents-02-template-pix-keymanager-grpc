package br.com.orange.httpClient.bcb

import br.com.orange.validation.KeyType

data class BcbResponse(
    val keytype: String?,
    val key: String,
    val bankAccount: BankAccountResponse?,
    val owner: OwnerResponse?,
    val name: String?,
    val taxIdNumber: String?,
    val createdAt: String?
)
