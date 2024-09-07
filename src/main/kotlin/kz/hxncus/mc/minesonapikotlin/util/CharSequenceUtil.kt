package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import java.text.Normalizer
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

private val convertPattern: Pattern = Pattern.compile("(?<=\\\\D)(?=\\\\d)|(?<=\\\\d)(?=\\\\D)")
private val normalizePattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
private val uuidPattern: Pattern = Pattern.compile("^\\{?[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\u200C\u200B}?$")

/**
 * String to millis long.
 * Converts string with number and time type to millis
 * For example:
 * 2s, 1d, 100, 200fps, 10d, etc.
 *
 * @return the long
 */
fun CharSequence.toMillis(): Long {
    val splitted = convertPattern.split(this)
    val parsedLong = splitted[0].toLong()
    if (splitted.size < 2) {
        return parsedLong
    }
    val lowerCase = splitted[1].lowercase()
    return when (lowerCase) {
        "tick", "ticks", "t" -> TimeUnit.MILLISECONDS.toTicks(1) * parsedLong
        "nanoseconds", "nanosecond", "ns" -> TimeUnit.NANOSECONDS.toMillis(parsedLong)
        "microseconds", "microsecond", "mcs" -> TimeUnit.MICROSECONDS.toMillis(parsedLong)
        "seconds", "second", "s" -> TimeUnit.SECONDS.toMillis(parsedLong)
        "minutes", "minute", "m" -> TimeUnit.MINUTES.toMillis(parsedLong)
        "hours", "hour", "h" -> TimeUnit.HOURS.toMillis(parsedLong)
        "days", "day", "d" -> TimeUnit.DAYS.toMillis(parsedLong)
        "fps", "framepersecond" -> (1000L / splitted[0].toDouble()).toLong()
        else -> parsedLong
    }
}

/**
 * Palindrome is a word, number, phrase, or another sequence
 * of characters which reads the same backward as forward,
 * this as madam, racecar. [OMIT] numeric palindromes,
 * particularly date/time stamps using short digits 11/11/11 11:11
 * and long digits 02/02/2020
 *
 * This function
 * @return return true if the text is a Palindrome
 */
fun CharSequence.isPalindrome(): Boolean {

    val normalizedText = this.normalize()
    for (i in normalizedText.indices) {
        if (normalizedText[i] != normalizedText[normalizedText.length - (i + 1)]) {
            return false
        }
    }
    return true
}


fun CharSequence.normalize(): String {
    val nfdNormalizedString = Normalizer.normalize(this, Normalizer.Form.NFD)
    return normalizePattern.matcher(nfdNormalizedString)
                           .replaceAll("")
                           .lowercase(Locale.getDefault())
                           .replace(" ", "")
}

fun CharSequence.isUuid(): Boolean {
    return uuidPattern.matcher(this).find()
}

fun String.toUuid(): UUID {
    return UUID.fromString(this) ?: EMPTY_UUID
}

fun String.colorize(): String {
    return MinesonAPI.plugin.colorManager.process(this)
}
