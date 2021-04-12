package br.com.orange.handler

import io.micronaut.aop.Around
import io.micronaut.context.annotation.Type
import javax.validation.Payload
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(CLASS, FILE, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
@Around
@Type(ExceptionHandlerInterceptor::class)
annotation class ErrorHandler(
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)