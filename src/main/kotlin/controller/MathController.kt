package controller

import api_rest_kotlin.converters.NumberConverter.convertToDouble
import api_rest_kotlin.converters.NumberConverter.isNumeric
import api_rest_kotlin.exceptions.UnsupporterMathOperationException
import api_rest_kotlin.math.SimpleMath
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class MathController {

    val counter: AtomicLong = AtomicLong()

    private val math: SimpleMath = SimpleMath()

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    fun sum(@PathVariable(value="numberOne") numberOne: String?,
            @PathVariable(value="numberTwo") numberTwo: String?,
            ): Double{
        if(!isNumeric(numberOne) || !isNumeric((numberTwo)))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return math.sum(convertToDouble(numberOne), convertToDouble(numberTwo))
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    fun subtraction(@PathVariable(value="numberOne") numberOne: String?,
                    @PathVariable(value="numberTwo") numberTwo: String?,
            ): Double{
        if(!isNumeric(numberOne) || !isNumeric((numberTwo)))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return math.subtraction(convertToDouble(numberOne), convertToDouble(numberTwo))
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    fun multiplication(@PathVariable(value="numberOne") numberOne: String?,
                       @PathVariable(value="numberTwo") numberTwo: String?,
            ): Double{
        if(!isNumeric(numberOne) || !isNumeric((numberTwo)))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return math.multiplication(convertToDouble(numberOne), convertToDouble(numberTwo))
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    fun division(@PathVariable(value="numberOne") numberOne: String?,
                 @PathVariable(value="numberTwo") numberTwo: String?,
            ): Double{
        if(!isNumeric(numberOne) || !isNumeric((numberTwo)))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return math.division(convertToDouble(numberOne), convertToDouble(numberTwo))
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    fun mean(@PathVariable(value="numberOne") numberOne: String?,
             @PathVariable(value="numberTwo") numberTwo: String?,
    ): Double{
        if(!isNumeric(numberOne) || !isNumeric((numberTwo)))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return math.mean(convertToDouble(numberOne), convertToDouble(numberTwo))
    }

    @RequestMapping("/squareRoot/{number}")
    fun squareRoot(@PathVariable(value="number") number: String?,
    ): Double{
        if(!isNumeric(number))
            throw UnsupporterMathOperationException("Please, set a numeric value")
        return math.squareRoot(convertToDouble(number))
    }
}