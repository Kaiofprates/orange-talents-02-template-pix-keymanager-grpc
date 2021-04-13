package br.com.orange.find.extensions

import br.com.orange.FindResponse
import br.com.orange.pix.PixKey

fun PixKey.toResponse(): FindResponse {
    return  FindResponse.newBuilder()
        .setId(this.id.toString())
        .setAgencia(this.account.agencia)
        .setCpf(this.account.cpfDoTitular)
        .setInstituicao(this.account.instituicao)
        .setNome(this.account.nomeDotitular)
        .setConta(this.account.numeroDaConta)
        .setTipoDeConta(this.accountType.toString())
        .setClientId(this.clientId.toString())
        .setCriadoEm(this.createdAt.toString())
        .setChave(this.pix)
        .setTipoDeChave(this.type.toString())
        .build()
}