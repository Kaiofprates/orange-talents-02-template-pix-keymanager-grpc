package br.com.orange.httpClient.bcb.request

import br.com.orange.validation.KeyType

data class BcbRequest(
   val keyType: KeyType,
   val key: String,
   val bankAccount: BankAccountRequest,
   val owner: OwnerRequest

)
