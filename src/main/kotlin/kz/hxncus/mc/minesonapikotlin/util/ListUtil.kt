package kz.hxncus.mc.minesonapikotlin.util

fun <T> List<T>.colorize(): List<T> {
    this.forEach { obj ->
        obj.toString().colorize()
    }
    return this
}
