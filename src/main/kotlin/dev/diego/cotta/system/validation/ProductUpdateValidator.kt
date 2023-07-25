package dev.diego.cotta.system.validation

import dev.diego.cotta.system.dto.request.ProductUpdateDto
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext


class ProductUpdateValidator : ConstraintValidator<RequestAnnotation?, ProductUpdateDto?> {
    override fun initialize(constraintAnnotation: RequestAnnotation?) = Unit
    override fun isValid(value: ProductUpdateDto?, context: ConstraintValidatorContext): Boolean {
        return value != null && (value.checkFields())
    }
}
