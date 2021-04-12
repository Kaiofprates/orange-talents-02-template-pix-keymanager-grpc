package br.com.orange.httpClient.bcb.request

import br.com.orange.httpClient.bcb.PersonType

data class OwnerRequest(
    val type: PersonType,
    val name: String,
    val taxIdNumber: String
)
