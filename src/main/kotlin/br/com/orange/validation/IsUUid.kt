package br.com.orange.validation

import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.constraints.Pattern
import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@Constraint(validatedBy = [])
@Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$",
        flags = [Pattern.Flag.CASE_INSENSITIVE])
annotation class IsUUid(
        val message: String = "Formato de UUID inválido",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<Payload>> = [],
)
