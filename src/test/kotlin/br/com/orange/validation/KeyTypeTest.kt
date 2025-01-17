package br.com.orange.validation

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
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
                Arguments.of("", KeyType.EMAIL),
                Arguments.of("mailmail.com", KeyType.EMAIL),
                Arguments.of("123456789asdf", KeyType.RANDOM),
        )



        @JvmStatic
        fun FormattedPix() = listOf(
                Arguments.of("56976710086", KeyType.CPF),
                Arguments.of("+5585988714077", KeyType.PHONE),
                Arguments.of("mailtest@mail.com", KeyType.EMAIL),
                Arguments.of("mailtest@mail.com.br", KeyType.EMAIL),
                Arguments.of("", KeyType.RANDOM),
        )
    }

    @ParameterizedTest
    @MethodSource("poorlyFormattedPix")
    @DisplayName("[teste unitário] Falha na formatação da chave")
    fun `falha no formato da chave`(keyValue: String, keyType: KeyType){
       assertFalse(keyType.valida(keyValue))
    }

    @ParameterizedTest
    @MethodSource("FormattedPix")
    @DisplayName("[teste unitário] sucesso na validação da chave pix")
    fun `chave no formato correto`(keyValue: String, keyType: KeyType){
        assertTrue(keyType.valida(keyValue))
    }


}