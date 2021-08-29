package vec.presentation.validation.constraints

import vec.presentation.validation.validator.PasswordConfirmationValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [PasswordConfirmationValidator::class])
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class PasswordConfirmation(
    val value: String,
    val message: String = "{vec.presentation.validation.constraints.PasswordConfirmation.message}",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
