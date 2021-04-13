package br.com.orange.find.extensions

import br.com.orange.FindResponse
import br.com.orange.httpClient.bcb.PixDetailsReponse

fun PixDetailsReponse.toResponse(): FindResponse{
    return FindResponse.newBuilder()
        .setAgencia(this.bankAccount.branch)
        .setCpf(this.owner.taxIdNumber)
        .setInstituicao(this.bankAccount.participant)
        .setNome(this.owner.name)
        .setConta(this.bankAccount.accountNumber)
        .setTipoDeConta(this.bankAccount.accountType)
        .setCriadoEm(this.createdAt)
        .setChave(this.key)
        .setTipoDeChave(this.keyType)
        .build()
}