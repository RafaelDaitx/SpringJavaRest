package api_rest_kotlin.exceptions

import java.lang.*
import kotlin.RuntimeException

class UnsupporterMathOperationException(exception: String?) : RuntimeException(exception)