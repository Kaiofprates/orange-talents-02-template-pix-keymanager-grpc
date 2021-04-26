package br.com.orange.shared

import io.micronaut.aop.Around
@Around
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class ErrorHandler {
}