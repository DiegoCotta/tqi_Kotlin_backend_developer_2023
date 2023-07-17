package dev.diego.cotta.system.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [ProductUpdateValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestAnnotation(
    val message: String = "{RequestAnnotation}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
    )
