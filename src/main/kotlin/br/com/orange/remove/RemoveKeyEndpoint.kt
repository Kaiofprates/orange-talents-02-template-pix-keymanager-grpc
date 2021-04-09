package br.com.orange.remove

import br.com.orange.KeymanagerServiceGrpc
import br.com.orange.RemoveRequest
import br.com.orange.RemoveResponse
import io.grpc.Status
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class RemoveKeyEndpoint(@Inject val removeKeyService: RemoveKeyService): KeymanagerServiceGrpc.KeymanagerServiceImplBase() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun removepix(request: RemoveRequest?,
                           responseObserver: StreamObserver<RemoveResponse>?) {

        try{
            /* passamos por um DTO
            *  para fazer uso das validações
            */
            val pixrequest = request?.toModel()

            if (pixrequest != null) {
                removeKeyService.removepix(pixrequest)
            }

            responseObserver?.onNext(RemoveResponse.newBuilder()
                    .setMessage("Chave removida com sucesso!")
                    .build())

            responseObserver?.onCompleted()

        }catch(error: PixNotExistsException){

            responseObserver?.onError(Status.NOT_FOUND
                    .withDescription(error.message)
                    .asRuntimeException())
        }

    }

}