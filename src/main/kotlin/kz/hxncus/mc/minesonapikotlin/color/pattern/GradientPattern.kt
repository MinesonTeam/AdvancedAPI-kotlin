package kz.hxncus.mc.minesonapikotlin.color.pattern

import kz.hxncus.mc.minesonapikotlin.MinesonAPI
import java.awt.Color

/**
 * Class Gradient pattern.
 *
 * @author Hxncus
 * @since 1.0.0
 */
class GradientPattern : Pattern {
    companion object {
        private val PATTERN: java.util.regex.Pattern = java.util.regex.Pattern.compile("[<{]#([A-Fa-f0-9]{6})[}>](((?![<{]#[A-Fa-f0-9]{6}[}>]).)*)[<{]/#([A-Fa-f0-9]{6})[}>]")
    }

    override fun process(message: String): String {
        var result = message
        val matcher = PATTERN.matcher(result)
        while (true) {
            val ifNotFound = !matcher.find()
            if (ifNotFound) {
                break
            }
            val start = matcher.group(1)
            val content = matcher.group(2)
            val end = matcher.group(4)
            result = result.replace(matcher.group(), MinesonAPI.plugin.colorManager.color(content, Color(start.toInt(16)), Color(end.toInt(16))))
        }
        return result
    }
}
