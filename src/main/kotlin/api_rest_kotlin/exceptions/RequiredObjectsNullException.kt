package api_rest_kotlin.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectsNullException : RuntimeException{
    constructor() : super("It`s not allowed to persist a null object!")
    constructor(exception: String?) : super(exception)
}