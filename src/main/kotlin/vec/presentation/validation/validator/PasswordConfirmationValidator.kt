package vec.presentation.validation.validator

import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl
import vec.presentation.validation.constraints.PasswordConfirmation
import java.util.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordConfirmationValidator : ConstraintValidator<PasswordConfirmation, Any> {

    private lateinit var constraintAnnotation: PasswordConfirmation

    override fun initialize(constraintAnnotation: PasswordConfirmation) {
        this.constraintAnnotation = constraintAnnotation
    }

    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        val beanWrapper: BeanWrapper = BeanWrapperImpl(value)

        val prop1: Any? = beanWrapper.getPropertyValue(constraintAnnotation.value)
        val prop2: Any? = beanWrapper.getPropertyValue("${constraintAnnotation.value}Confirmation")

        return if (Objects.equals(prop1, prop2)) {
            true
        } else {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate(constraintAnnotation.message)
                .addPropertyNode("${constraintAnnotation.value}Confirmation")
                .addConstraintViolation()

            false
        }
    }

}
