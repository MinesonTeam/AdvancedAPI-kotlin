package kz.hxncus.mc.minesonapikotlin.util

import java.util.regex.Pattern

private val PATTERN: Pattern = Pattern.compile("(?<=\\\\D)(?=\\\\d)|(?<=\\\\d)(?=\\\\D)")

/**
 * The Milliseconds in second.
 */
const val MILLISECONDS_IN_SECOND: Long = 1000L

/**
 * The Seconds in minute.
 */
const val SECONDS_IN_MINUTE: Int = 60

/**
 * The Minutes in hour.
 */
const val MINUTES_IN_HOUR: Short = 60

/**
 * The Milliseconds in tick.
 */
const val MILLISECONDS_IN_TICK: Long = 50L

/**
 * Hours to millis long.
 *
 * @param hours the hours
 * @return the long
 */
fun hoursToMillis(hours: Long): Long {
    return minutesToMillis(hours * MINUTES_IN_HOUR)
}

/**
 * Minutes to millis long.
 *
 * @param minutes the minutes
 * @return the long
 */
fun minutesToMillis(minutes: Long): Long {
    return secondsToMillis(minutes * SECONDS_IN_MINUTE)
}

/**
 * Seconds to millis long.
 *
 * @param seconds the seconds
 * @return the long
 */
fun secondsToMillis(seconds: Long): Long {
    return seconds * MILLISECONDS_IN_SECOND
}

/**
 * String to millis long.
 *
 * @param input the input
 * @return the long
 */
fun stringToMillis(input: CharSequence): Long {
    val splitted = PATTERN.split(input)
    val parsedLong = splitted[0].toLong()
    if (splitted.size < 2) {
        return parsedLong
    }
    val lowerCase = splitted[1].lowercase()
    return when (lowerCase) {
        "tick", "ticks", "t" -> ticksToMillis(parsedLong)
        "seconds", "second", "s" -> secondsToMillis(parsedLong)
        "minutes", "minute", "m" -> minutesToMillis(parsedLong)
        "fps", "framepersecond" -> fpsToMillis(splitted[0].toDouble())
        else -> parsedLong
    }
}

/**
 * Ticks to millis long.
 *
 * @param ticks the ticks
 * @return the long
 */
fun ticksToMillis(ticks: Long): Long {
    return ticks * MILLISECONDS_IN_TICK
}

/**
 * Fps to millis long.
 *
 * @param fps the fps
 * @return the long
 */
fun fpsToMillis(fps: Double): Long {
    return (MILLISECONDS_IN_SECOND / fps).toLong()
}
