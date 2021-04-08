package br.com.orange.validations

import io.grpc.Status

class PhoneValitor(var phone: String) {
    fun valid(): String {
        if(!phone.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())){
            throw Status.INVALID_ARGUMENT
                    .withDescription("Telefone inválido")
                    .augmentDescription("verifique os dígitos e tente novamente")
                    .asRuntimeException()
        }else{
            return phone
        }
    }
}