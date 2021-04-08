package br.com.orange.validations

import io.grpc.Status

class EmailValidator( var email: String) {
    fun valid(): String{
        if(!email.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$".toRegex())){
            throw Status.INVALID_ARGUMENT
                    .withDescription("Formato de email inválido")
                    .augmentDescription("verifique os dígitos e tente novamente")
                    .asRuntimeException()
        }else{
            return email
        }
    }
}