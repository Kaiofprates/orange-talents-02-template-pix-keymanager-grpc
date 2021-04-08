package br.com.orange.validation

import br.com.orange.register.NewPixKey
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS,AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidPixKeyValidator::class])
annotation class ValidPixKey(
        val message: String = "Chave Pix inválida (\${validatedValue.type})",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<Payload>> = []
) {
    /**
     * @author Rafael Ponte
     */
}

@Singleton
class ValidPixKeyValidator: ConstraintValidator<ValidPixKey,NewPixKey>{
    override fun isValid(value: NewPixKey?,
                         annotationMetadata: AnnotationValue<ValidPixKey>,
                         context: ConstraintValidatorContext): Boolean {
        if(value?.type == null){
            return false
        }

        return value.type.valida(value.pix)

    }

}