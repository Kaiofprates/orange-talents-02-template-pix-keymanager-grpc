package br.com.orange.validations

import io.grpc.Status

class CpfValidator(var cpf: String ) {

    fun isCPF(document: String): Boolean {
        if(document.equals(null)) return false

        val numbers = arrayListOf<Int>()

        document.filter { it.isDigit() }
                .forEach { numbers.add(it.toString().toInt())}

        if(numbers.size != 11) return false
        // repetição
        (0..9).forEach{
            n -> val digits = arrayListOf<Int>()
            (0..10).forEach{
                digits.add(n)
            }
            if(numbers == digits) return false
        }

        // digito 1
        val dv1 = ((0..8).sumBy { (it + 1) * numbers[it] }).rem(11)
                .let { if ( it >= 10 ) 0 else it  }

        // digito 2
        val dv2 = ((0..8).sumBy { it * numbers[it] }.let { (it + (dv1 * 9 )).rem(11) })
                .let { if (it >= 10) 0 else it }
        return  numbers[9] == dv1 && numbers[10] == dv2
    }

    fun format(): String{
        if(!isCPF(cpf)){
            throw Status.INVALID_ARGUMENT
                    .withDescription("CPF inválido")
                    .augmentDescription("verifique os dígitos e tente novamente")
                    .asRuntimeException()
        }else{
            var retorno = cpf.filter { it.isDigit() }
            return retorno
        }
    }

}