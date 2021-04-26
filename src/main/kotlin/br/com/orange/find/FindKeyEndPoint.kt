package br.com.orange.find

import br.com.orange.FindRequest
import br.com.orange.FindResponse
import br.com.orange.KeymanagerFindKeyGrpc
import br.com.orange.find.dto.FindRequestDto
import br.com.orange.find.dto.PixKeyDto
import br.com.orange.shared.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class FindKeyEndPoint(@Inject val service: FindKeyService
): KeymanagerFindKeyGrpc.KeymanagerFindKeyImplBase() {

    override fun findByKey(
        request: FindRequest?,
        responseObserver: StreamObserver<FindResponse>?
    ) {

            var pix: FindResponse

            if(request?.pixkey == ""){
                pix = service.getPix(FindRequestDto(request.pixId.id, request.pixId.clientId))
            } else {
                pix = service.getPix(PixKeyDto(request?.pixkey!!))
            }

            responseObserver?.onNext(pix)
            responseObserver?.onCompleted()


    }



}