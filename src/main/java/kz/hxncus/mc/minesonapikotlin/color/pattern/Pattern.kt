package kz.hxncus.mc.minesonapikotlin.color.pattern

/**
 * The interface Pattern.
 * @author Hxncus
 * @since  1.0.0
 */
interface Pattern {
    /**
     * Process string.
     *
     * @param message the message
     * @return the string
     */
    fun process(message: String): String
}
