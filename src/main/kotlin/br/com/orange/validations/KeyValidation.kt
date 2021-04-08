package br.com.orange.validations

import br.com.orange.Keytype
import br.com.orange.RegisterRequest
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class KeyValidation(var request: RegisterRequest) {

    // refatorar

    fun valid(): String{
       return  when(request.type){
           Keytype.CPF -> CpfValidator(request.value).format()
           Keytype.PHONE -> PhoneValitor(request.value).valid()
           Keytype.MAIL -> EmailValidator(request.value).valid()
           else -> ""
       }
    }


}

