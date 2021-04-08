package br.com.orange.validation

import io.micronaut.validation.validator.constraints.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator

enum class KeyType {
    CPF {
        override fun valida(key: String?): Boolean {
            if(key.isNullOrBlank()){
                return false
            }
            if(!key.matches("[0-9]+".toRegex())){
                return false
            }
            return CPFValidator().run {
                initialize(null)
                isValid(key, null)
            }
        }
    },
    PHONE {
        override fun valida(key: String?): Boolean{
            if(key.isNullOrBlank()){
                return false
            }
            return key.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },
    MAIL {
        override fun valida(key: String?): Boolean {
            if(key.isNullOrBlank()){
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(key, null)
            }
        }
    },
    RANDOM{
        override fun valida(key: String?) = key.isNullOrBlank()
    };

    abstract fun valida(key: String?): Boolean
}