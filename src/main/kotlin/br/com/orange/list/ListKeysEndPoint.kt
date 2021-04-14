package br.com.orange.list

import br.com.orange.KeymanagerListServiceGrpc
import br.com.orange.ListRequest
import br.com.orange.ListResponse
import br.com.orange.list.dto.RequestDTO
import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class ListKeysEndPoint(@Inject val listService: ListKeysService): KeymanagerListServiceGrpc.KeymanagerListServiceImplBase() {

    override fun listKey(request: ListRequest?, responseObserver: StreamObserver<ListResponse>?) {

        try{
           val response  =  listService.getAll(RequestDTO(clientId = request!!.clientId))

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()

        }catch (error: ConstraintViolationException){
            responseObserver?.onError(Status.INVALID_ARGUMENT
                .withDescription("Client ID mal formatado ou nulo")
                .asRuntimeException())
        }

    }


}