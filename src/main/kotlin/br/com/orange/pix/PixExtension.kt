package br.com.orange.pix

import br.com.orange.Account
import br.com.orange.httpClient.bcb.AccountType
import br.com.orange.httpClient.bcb.PersonType
import br.com.orange.httpClient.bcb.request.BankAccountRequest
import br.com.orange.httpClient.bcb.request.BcbRequest
import br.com.orange.httpClient.bcb.request.OwnerRequest
import io.grpc.Status
import io.grpc.StatusRuntimeException
import java.lang.RuntimeException

fun PixKey.toModel(): BcbRequest {

    val bankAccountRequest = BankAccountRequest(
    participant = "60701190",
    branch = this.account.agencia,
    accountNumber = this.account.numeroDaConta,
    accountType = when (this.accountType){
        Account.SAVINGS -> AccountType.SVGS
        Account.CHECKING -> AccountType.CACC
        else -> throw StatusRuntimeException(Status.INVALID_ARGUMENT)
    })

    val ownerRequest = OwnerRequest(
        type = PersonType.NATURAL_PERSON,
        name = this.account.nomeDotitular,
        taxIdNumber = this.account.cpfDoTitular
    )

    return BcbRequest(
     keyType = this.type,
     key = this.pix,
     bankAccount =  bankAccountRequest,
     owner =  ownerRequest
    )
}

fun PixKey.update(key: String): PixKey{
    this.pix = key
    return this
}