package kz.hxncus.mc.minesonapikotlin.color.pattern

import kz.hxncus.mc.minesonapikotlin.MinesonAPI

/**
 * Class Solid pattern.
 *
 * @author Hxncus
 * @since 1.0.0
 */
class SolidPattern : Pattern {
    companion object {
        /**
         * The constant PATTERN.
         */
        val PATTERN: java.util.regex.Pattern = java.util.regex.Pattern.compile("[<{]#([A-Fa-f0-9]{6})[}>]|&?#([A-Fa-f0-9]{6})")
    }

    override fun process(message: String): String {
        var result = message
        val matcher = PATTERN.matcher(result)
        while (true) {
            val ifNotFound = !matcher.find()
            if (ifNotFound) {
                break
            }
            var color = matcher.group(1)
            if (color == null) {
                color = matcher.group(2)
            }
            result = result.replace(matcher.group(), java.lang.String.valueOf(MinesonAPI.plugin.colorManager.getColor(color)))
        }
        return result
    }
}
