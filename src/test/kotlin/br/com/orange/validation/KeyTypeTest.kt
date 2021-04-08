package br.com.orange.validation

import br.com.orange.Account
import br.com.orange.Keytype
import br.com.orange.RegisterRequest
import br.com.orange.register.PixRegisterEndpointTest
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@MicronautTest
internal class KeyTypeTest(){

    companion object {
        @JvmStatic
        fun poorlyFormattedPix() = listOf(
                Arguments.of("", KeyType.CPF),
                Arguments.of("569.767.100-86", KeyType.CPF),
                Arguments.of("355.336.50059", KeyType.CPF),
                Arguments.of("569.76710086", KeyType.CPF),
                Arguments.of("", KeyType.PHONE),
                Arguments.of("+5585988714077a", KeyType.PHONE),
                Arguments.of("5585988714077", KeyType.PHONE),
                Arguments.of("", KeyType.MAIL),
                Arguments.of("mailmail.com", KeyType.MAIL),
                Arguments.of("123456789asdf", KeyType.RANDOM),
        )
    }

    @ParameterizedTest
    @MethodSource("poorlyFormattedPix")
    @DisplayName("[teste unitário] Falha na formatação da chave")
    fun `falha no formato da chave`(keyValue: String, keyType: KeyType){
       assertFalse(keyType.valida(keyValue))
    }


}