package kz.hxncus.mc.minesonapikotlin.random

import java.util.*
import java.util.stream.Collectors

/**
 * The type Key generator.
 *
 * @param <T> the type parameter
 * @author Hxncus
 * @since  1.0.0
 */
class KeyGenerator<T> (private val random: Random) {
    companion object {
        /**
         * The constant MAX_PERCENT.
         */
        const val MAX_PERCENT: Double = 100.0
    }

    private val stringEntryMap: MutableMap<T, Map.Entry<Double, Double>> = HashMap(32)

    /**
     * Generate key t.
     *
     * @return the t
     */
    fun generateKey(): T {
        return this.getKey(random.nextInt(MAX_PERCENT.toInt()) + 1.0)
    }

    /**
     * Gets key.
     *
     * @param random the random
     * @return the key
     */
    fun getKey(random: Double): T {
        var `val` = random
        for ((key, value) in this.convertToSimilarRanges()) {
            if (`val` <= value) {
                return key
            }
            `val` = Math.round((`val` - value) * MAX_PERCENT) / MAX_PERCENT
        }
        throw RuntimeException("KeyGenerator is not configured correctly.")
    }

    private fun convertToSimilarRanges(): Set<AbstractMap.SimpleEntry<T, Double>> {
        return stringEntryMap.entries.stream()
            .map { entry: Map.Entry<T, Map.Entry<Double, Double>> ->
                AbstractMap.SimpleEntry(
                    entry.key,
                    entry.value.key / entry.value.value * 100
                )
            }
            .collect(Collectors.toSet())
    }

    /**
     * Add key generator.
     *
     * @param key    the key
     * @param chance the chance
     * @param range  the range
     * @return the key generator
     */
    /**
     * Add key generator.
     *
     * @param key    the key
     * @param chance the chance
     * @return the key generator
     */
    @JvmOverloads
    fun add(key: T, chance: Double, range: Double = 100.0): KeyGenerator<T> {
        if (chance > range) {
            throw RuntimeException("chance can`t be greater than range")
        }
        stringEntryMap[key] = AbstractMap.SimpleEntry(chance, range)
        return this
    }

    /**
     * Add all key generators.
     *
     * @param map the map
     * @return the key generator
     */
    fun addAll(map: Map<out T, Map.Entry<Double, Double>>?): KeyGenerator<T> {
        stringEntryMap.putAll(map!!)
        return this
    }

    /**
     * Remove key generator.
     *
     * @param key the key
     * @return the key generator
     */
    fun remove(key: T): KeyGenerator<T> {
        stringEntryMap.remove(key)
        return this
    }

    /**
     * Clear key generator.
     *
     * @return the key generator
     */
    fun clear(): KeyGenerator<T> {
        stringEntryMap.clear()
        return this
    }
}
