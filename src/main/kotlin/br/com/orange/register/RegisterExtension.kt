package br.com.orange.register

import br.com.orange.Account
import br.com.orange.Keytype
import br.com.orange.RegisterRequest

fun RegisterRequest.toModel(): NewPixKey {
    return NewPixKey(
            clientId = clientId,
            pix = value,
            type = when (type) {
                Keytype.UNKNOW_TYPE -> null
                else -> Keytype.valueOf(type.name)
            },
            account = when (account){
                Account.UNKNOW_ACCOUNT -> null
                else -> Account.valueOf(account.name)
            })
}