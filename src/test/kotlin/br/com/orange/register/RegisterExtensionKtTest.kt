package br.com.orange.register

import br.com.orange.Account
import br.com.orange.Keytype
import br.com.orange.RegisterRequest
import br.com.orange.pix.AssocietedAccount
import br.com.orange.validation.KeyType
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@MicronautTest
internal class RegisterExtensionKtTest(){


    companion object {
        @JvmStatic
        fun createPix() = listOf(
                Arguments.of("56976710086", KeyType.CPF),
                Arguments.of("+5585988714077", KeyType.PHONE),
                Arguments.of("mailtest@mail.com", KeyType.MAIL),
        )
    }


    @Test
    @DisplayName("Testa o comportamento criacão de pix aleatório")
    fun `random pix test`(){
        val register  = RegisterRequest.newBuilder()
                .setType(Keytype.RANDOM)
                .setClientId("c56dfef4-7901-44fb-84e2-a2cefb157890")
                .setAccount(Account.SAVINGS)
                .setValue("")
                .build()
                .toModel()
                .toModel(
                        AssocietedAccount("ITAÚ UNIBANCO S.A.",
                                "Rafael M C Ponte",
                                "02467781054",
                                "0001",
                                "291900")
                ).also {
                    val check = it.pix.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$".toRegex())
                    assertTrue(check)
                }
    }


    @ParameterizedTest
    @MethodSource("createPix")
    fun `create pix test`(key: String, type: KeyType){
        RegisterRequest.newBuilder()
                .setType(Keytype.valueOf(type.name))
                .setClientId("c56dfef4-7901-44fb-84e2-a2cefb157890")
                .setAccount(Account.SAVINGS)
                .setValue(key)
                .build()
                .toModel()
                .toModel(
                        AssocietedAccount("ITAÚ UNIBANCO S.A.",
                                "Rafael M C Ponte",
                                "02467781054",
                                "0001",
                                "291900")
                ).also {
                    assertEquals(key,it.pix)
                }
    }


}