package kz.hxncus.mc.minesonapikotlin.color

import com.google.common.collect.ImmutableMap
import kz.hxncus.mc.minesonapikotlin.color.caching.LruCache
import kz.hxncus.mc.minesonapikotlin.color.pattern.GradientPattern
import kz.hxncus.mc.minesonapikotlin.color.pattern.Pattern
import kz.hxncus.mc.minesonapikotlin.color.pattern.RainbowPattern
import kz.hxncus.mc.minesonapikotlin.color.pattern.SolidPattern
import kz.hxncus.mc.minesonapikotlin.util.IS_HEX_VERSION
import kz.hxncus.mc.minesonapikotlin.util.reflect.ReflectMethod
import net.md_5.bungee.api.ChatColor
import java.awt.Color
import kotlin.math.abs

/**
 * Class Color manager.
 *
 * @author Hxncus
 * @since 1.0.
 */
class ColorManager {
    companion object {
        /**
         * The constant BUKKIT_COLOR_CHAR.
         */
        const val BUKKIT_COLOR_CHAR: Char = '&'
        private val PATTERN: java.util.regex.Pattern = java.util.regex.Pattern.compile("&\\w{5,8}(:[0-9A-F]{6})?>")
    }

    private val methodOf: ReflectMethod = ReflectMethod(ChatColor::class.java, "of", Color::class.java)
    private val specialColors: List<String> = mutableListOf("&l", "&n", "&o", "&k", "&m")
    private val lruCache: LruCache = LruCache(1)
    private val colors: ImmutableMap<Any, Any> = ImmutableMap.builder<Any, Any>()
        .put(Color(0), ChatColor.getByChar('0'))
        .put(Color(170), ChatColor.getByChar('1'))
        .put(Color(43520), ChatColor.getByChar('2'))
        .put(Color(43690), ChatColor.getByChar('3'))
        .put(Color(11141120), ChatColor.getByChar('4'))
        .put(Color(11141290), ChatColor.getByChar('5'))
        .put(Color(16755200), ChatColor.getByChar('6'))
        .put(Color(11184810), ChatColor.getByChar('7'))
        .put(Color(5592405), ChatColor.getByChar('8'))
        .put(Color(5592575), ChatColor.getByChar('9'))
        .put(Color(5635925), ChatColor.getByChar('a'))
        .put(Color(5636095), ChatColor.getByChar('b'))
        .put(Color(16733525), ChatColor.getByChar('c'))
        .put(Color(16733695), ChatColor.getByChar('d'))
        .put(Color(16777045), ChatColor.getByChar('e'))
        .put(Color(16777215), ChatColor.getByChar('f'))
        .build()

    private val patterns: List<Pattern> = listOf<Pattern>(GradientPattern(), SolidPattern(), RainbowPattern())

    /**
     * Process list.
     *
     * @param strings the strings
     * @return the list
     */
    fun process(strings: List<String>): List<String> {
        strings.toMutableList().replaceAll { message: String -> this.process(message) }
        return strings
    }

    /**
     * Process message.
     *
     * @param message the message
     * @return the message
     */
    fun process(message: String): String {
        val result: String = lruCache.getResult(message).toString()
        return result
    }

    /**
     * Color message.
     *
     * @param message the message
     * @param color   the color
     * @return the message
     */
    fun color(message: String, color: Color): String {
        return (if (IS_HEX_VERSION) methodOf.invokeStatic(color) else this.getClosestColor(color)).toString() + message
    }

    private fun getClosestColor(color: Color): ChatColor {
        var nearestColor: Color? = null
        var nearestDistance = 2.147483647E9
        for (colorObject in colors.keys) {
            val constantColor = colorObject as Color
            val distance = StrictMath.pow((color.red - constantColor.red).toDouble(), 2.0) + StrictMath.pow(
                (color.green - constantColor.green).toDouble(), 2.0
            ) + StrictMath.pow((color.blue - constantColor.blue).toDouble(), 2.0)
            if (nearestDistance > distance) {
                nearestColor = constantColor
                nearestDistance = distance
            }
        }
        return colors[nearestColor] as ChatColor
    }

    /**
     * Color message.
     *
     * @param message the message
     * @param start   the start
     * @param end     the end
     * @return the message
     */
    fun color(message: String, start: Color, end: Color): String {
        var str = message
        val colorsBuilder = StringBuilder(specialColors.size)
        for (color in this.specialColors) {
            if (str.contains(color)) {
                colorsBuilder.append(color)
                str = str.replace(color, "")
            }
        }
        val length = str.length
        val stringBuilder = StringBuilder(length * 3)
        val gradient = this.createGradient(start, end, length)
        val pattern = java.util.regex.Pattern.compile("")
        val characters = pattern.split(str)
        for (i in 0 until length) stringBuilder.append(gradient[i])
            .append(colorsBuilder)
            .append(characters[i])
        return stringBuilder.toString()
    }

    private fun createGradient(start: Color, end: Color, step: Int): Array<ChatColor?> {
        if (step <= 1) return arrayOf(ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE)
        val chatColors = arrayOfNulls<ChatColor>(step)
        val stepR = (abs((start.red - end.red).toDouble()) / (step - 1)).toInt()
        val stepG = (abs((start.green - end.green).toDouble()) / (step - 1)).toInt()
        val stepB = (abs((start.blue - end.blue).toDouble()) / (step - 1)).toInt()
        val direction = intArrayOf(
            if ((start.red < end.red)) 1 else -1,
            if ((start.green < end.green)) 1 else -1,
            if ((start.blue < end.blue)) 1 else -1
        )
        for (i in 0 until step) {
            val color = Color(
                start.red + stepR * i * direction[0],
                start.green + stepG * i * direction[1],
                start.blue + stepB * i * direction[2]
            )
            if (IS_HEX_VERSION) {
                chatColors[i] = methodOf.invokeStatic(color)
            } else {
                chatColors[i] = this.getClosestColor(color)
            }
        }
        return chatColors
    }

    /**
     * A rainbow message.
     *
     * @param message     the message
     * @param saturation the saturation
     * @return the message
     */
    fun rainbow(message: String, saturation: Float): String {
        var str = message
        val colorsBuilder = StringBuilder(specialColors.size)
        for (color in this.specialColors) {
            if (str.contains(color)) {
                colorsBuilder.append(color)
                str = str.replace(color, "")
            }
        }
        val length = str.length
        val stringBuilder = StringBuilder(length * 3)
        val pattern = java.util.regex.Pattern.compile("")
        val rainbow = this.createRainbow(length, saturation)
        val characters = pattern.split(str)
        for (i in 0 until length) stringBuilder.append(rainbow[i])
            .append(colorsBuilder)
            .append(characters[i])
        return stringBuilder.toString()
    }

    private fun createRainbow(step: Int, saturation: Float): Array<ChatColor?> {
        val chatColors = arrayOfNulls<ChatColor>(step)
        val colorStep = 1.0 / step
        for (i in 0 until step) {
            val color = Color.getHSBColor((colorStep * i).toFloat(), saturation, saturation)
            if (IS_HEX_VERSION) {
                chatColors[i] = methodOf.invokeStatic(color)
            } else {
                chatColors[i] = this.getClosestColor(color)
            }
        }
        return chatColors
    }

    /**
     * Gets color.
     *
     * @param message the message
     * @return the color
     */
    fun getColor(message: String): ChatColor {
        return if (IS_HEX_VERSION) methodOf.invokeStatic(Color(message.toInt(16))) else getClosestColor(Color(message.toInt(16)))
    }

    /**
     * Strip color formatting message.
     *
     * @param message the message
     * @return the message
     */
    fun stripColorFormatting(message: CharSequence): String {
        return PATTERN.matcher(message).replaceAll("")
    }
}

