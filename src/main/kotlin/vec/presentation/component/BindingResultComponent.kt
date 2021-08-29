package vec.presentation.component

import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult

@Component
class BindingResultComponent {

    fun rejectValue(bindingResult: BindingResult, field: String, errorCode: String) {
        val target: Any? = bindingResult.target
        checkNotNull(target)

        bindingResult.rejectValue(field, "${target::class.qualifiedName}.${field}.${errorCode}")
    }

}
