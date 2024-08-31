package kz.hxncus.mc.minesonapikotlin.color.pattern

import kz.hxncus.mc.minesonapikotlin.MinesonAPI

/**
 * Class a Rainbow pattern.
 *
 * @author Hxncus
 * @since 1.0.0
 */
class RainbowPattern : Pattern {
    companion object {
        private val PATTERN: java.util.regex.Pattern = java.util.regex.Pattern.compile("<RAINBOW(\\d{1,3})>(.*?)</RAINBOW>")
    }

    override fun process(message: String): String {
        var result = message
        val matcher = PATTERN.matcher(result)
        while (true) {
            val ifNotFound = !matcher.find()
            if (ifNotFound) {
                break
            }
            val saturation = matcher.group(1)
            val content = matcher.group(2)
            result = result.replace(matcher.group(), MinesonAPI.plugin.colorManager.rainbow(content, saturation.toFloat()))
        }
        return result
    }
}