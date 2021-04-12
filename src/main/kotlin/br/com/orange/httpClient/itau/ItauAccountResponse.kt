package br.com.orange.httpClient

import br.com.orange.pix.AssocietedAccount

data class ItauAccountResponse(
        val tipo: String,
        val instituicao: InstituicaoResponse,
        val agencia: String,
        val numero: String,
        val titular: TitularResponse
) {

    fun toModel(): AssocietedAccount {
        return AssocietedAccount(
                instituicao = this.instituicao.nome,
                nomeDotitular = this.titular.nome,
                cpfDoTitular = this.titular.cpf,
                agencia = this.agencia,
                numeroDaConta = this.numero
        )
    }


}

data class InstituicaoResponse(val nome: String, val ispb: String)
data class TitularResponse( val nome: String, val cpf: String)
