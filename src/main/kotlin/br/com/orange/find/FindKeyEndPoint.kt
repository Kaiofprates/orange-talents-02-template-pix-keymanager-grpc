package br.com.orange.find

import br.com.orange.FindRequest
import br.com.orange.FindResponse
import br.com.orange.KeymanagerFindKeyGrpc
import br.com.orange.find.dto.FindRequestDto
import br.com.orange.find.dto.PixKeyDto
import br.com.orange.remove.PixNotExistsException
import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class FindKeyEndPoint(@Inject val service: FindKeyService
): KeymanagerFindKeyGrpc.KeymanagerFindKeyImplBase() {

    override fun findById(
        request: FindRequest?,
        responseObserver: StreamObserver<FindResponse>?
    ) {

        try {

            // TODO: 13/04/2021  primeira implementação funcional para a escolha entre as duas formas de pesquisa - REFATORAR (se possível)

            var pix: FindResponse

            if(request?.pixkey == ""){
                pix = service.getPix(FindRequestDto(request.pixId.id, request.pixId.clientId))
            } else {
                pix = service.getPix(PixKeyDto(request?.pixkey!!))
            }

            responseObserver?.onNext(pix)
            responseObserver?.onCompleted()

        }catch (error: PixNotExistsException){
            responseObserver?.onError(Status.NOT_FOUND
                .withDescription(error.message)
                .asRuntimeException())
        }catch (error: ConstraintViolationException){
            responseObserver?.onError(Status.INVALID_ARGUMENT
                .withDescription("O id especificado não possui formato válido")
                .asRuntimeException())
        }

    }



}