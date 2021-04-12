package br.com.orange.httpClient.bcb.request

import br.com.orange.httpClient.bcb.AccountType

data class BankAccountRequest (
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: AccountType
    )