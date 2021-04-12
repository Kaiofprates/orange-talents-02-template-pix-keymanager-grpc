package br.com.orange.httpClient.bcb

data class BankAccountResponse(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: AccountType
)

enum class AccountType{
    CACC,SVGS
}