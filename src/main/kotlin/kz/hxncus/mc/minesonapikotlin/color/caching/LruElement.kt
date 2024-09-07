package kz.hxncus.mc.minesonapikotlin.color.caching

/**
 * Class Lru element.
 *
 * @author Hxncus
 * @since 1.0.0
 */
class LruElement (private val input: String, private val result: String) {
    private fun canEqual(other: Any): Boolean {
        return other is LruElement
    }

    /**
     * Input string.
     *
     * @return the string
     */
    fun input(): String {
        return this.input
    }

    /**
     * Result string.
     *
     * @return the string
     */
    fun result(): String {
        return this.result
    }
}
