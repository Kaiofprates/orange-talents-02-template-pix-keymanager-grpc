package br.com.orange.list

import br.com.orange.KeymanagerListServiceGrpc
import br.com.orange.ListRequest
import br.com.orange.ListResponse
import br.com.orange.list.dto.RequestDTO
import br.com.orange.shared.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class ListKeysEndPoint(@Inject val listService: ListKeysService): KeymanagerListServiceGrpc.KeymanagerListServiceImplBase() {

    override fun listKey(request: ListRequest?, responseObserver: StreamObserver<ListResponse>?) {

        val response  =  listService.getAll(RequestDTO(clientId = request!!.clientId))
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()

    }


}