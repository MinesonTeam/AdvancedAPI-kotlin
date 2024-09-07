package kz.hxncus.mc.minesonapikotlin.util

import java.util.concurrent.TimeUnit

fun TimeUnit.toTicks(duration: Long): Long {
    return toSeconds(duration) * 20L
}
