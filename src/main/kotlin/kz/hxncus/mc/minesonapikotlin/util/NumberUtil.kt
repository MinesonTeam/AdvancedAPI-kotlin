package kz.hxncus.mc.minesonapikotlin.util

import java.security.InvalidParameterException

/**
 * Calculates the factorial of a natural number greater than or equal to 0 recursively.
 * @return The factorial of the number passed as parameter.
 */
fun Number.factorial(): Long {
    val longVal = this.toLong()
    if (longVal < 0L) {
        throw InvalidParameterException("The number of which to calculate the factorial must be greater or equal to zero.")
    } else return when (this) {
        0L -> 1
        1L -> longVal
        else -> longVal * (longVal - 1).factorial()
    }
}

fun Number.isPrime(): Boolean {
    val doubleVal = this.toDouble()
    return doubleVal > 1 && (2..((doubleVal / 2).toInt())).all { doubleVal % it != 0.0 }
}
