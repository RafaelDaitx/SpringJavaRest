package controller

import api_rest_kotlin.exceptions.UnsupporterMathOperationException
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class MathController2 {

    val counter: AtomicLong = AtomicLong()


    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    fun sum(@PathVariable(value="numberOne") numberOne: String?,
            @PathVariable(value="numberTwo") numberTwo: String?,
            ): Double{
        if(!isNumeric(numberOne) || !isNumeric((numberTwo)))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return convertToDouble(numberOne) + convertToDouble(numberTwo)
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    fun subtraction(@PathVariable(value="numberOne") numberOne: String?,
                    @PathVariable(value="numberTwo") numberTwo: String?,
            ): Double{
        if(!isNumeric(numberOne) || !isNumeric((numberTwo)))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return convertToDouble(numberOne) - convertToDouble(numberTwo)
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    fun multiplication(@PathVariable(value="numberOne") numberOne: String?,
                       @PathVariable(value="numberTwo") numberTwo: String?,
            ): Double{
        if(!isNumeric(numberOne) || !isNumeric((numberTwo)))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return convertToDouble(numberOne) * convertToDouble(numberTwo)
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    fun division(@PathVariable(value="numberOne") numberOne: String?,
                 @PathVariable(value="numberTwo") numberTwo: String?,
            ): Double{
        if(!isNumeric(numberOne) || !isNumeric((numberTwo)))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return convertToDouble(numberOne) / convertToDouble(numberTwo)
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    fun mean(@PathVariable(value="numberOne") numberOne: String?,
             @PathVariable(value="numberTwo") numberTwo: String?,
    ): Double{
        if(!isNumeric(numberOne) || !isNumeric((numberTwo)))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2
    }

    @RequestMapping("/squareRoot/{number}")
    fun squareRoot(@PathVariable(value="number") number: String?,
    ): Double{
        if(!isNumeric(number))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return Math.sqrt(convertToDouble(number))
    }

    private fun convertToDouble(strNumber: String?): Double {
        if(strNumber.isNullOrBlank()) return 0.0

        val number = strNumber.replace(",".toRegex(), ".")
        return if(isNumeric(number)) number.toDouble() else 0.0
    }

    private fun isNumeric(strNumber: String?): Boolean {
        if(strNumber.isNullOrBlank()) return false

        val number = strNumber.replace(",".toRegex(), ".")
        return number.matches("""[-+]?[0-9]*\.?[0-9]+""".toRegex())
    }
}