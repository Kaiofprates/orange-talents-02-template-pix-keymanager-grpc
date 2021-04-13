package br.com.orange.find

import br.com.orange.FindRequest
import br.com.orange.FindResponse
import br.com.orange.KeymanagerFindKeyGrpc
import br.com.orange.remove.PixNotExistsException
import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FindKeyEndPoint(@Inject val service: FindKeyService
): KeymanagerFindKeyGrpc.KeymanagerFindKeyImplBase() {

    override fun findById(request: FindRequest?, responseObserver: StreamObserver<FindResponse>?) {


        try {
            // TODO: 12/04/2021  fazer a tratativa do UUID
            val pix = service.getPix(request?.id!!)

            responseObserver?.onNext(
                FindResponse.newBuilder()
                    .setId(pix.id.toString())
                    .setAgencia(pix.account.agencia)
                    .setCpf(pix.account.cpfDoTitular)
                    .setInstituicao(pix.account.instituicao)
                    .setNome(pix.account.nomeDotitular)
                    .setConta(pix.account.numeroDaConta)
                    .setTipoDeConta(pix.accountType.toString())
                    .setClientId(pix.clientId.toString())
                    .setCriadoEm(pix.createdAt.toString())
                    .setChave(pix.pix)
                    .setTipoDeChave(pix.type.toString())
                    .build()
            )
            responseObserver?.onCompleted()
        }catch (error: PixNotExistsException){
            responseObserver?.onError(Status.NOT_FOUND
                .withDescription(error.message)
                .asRuntimeException())
        }


    }

}