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
class Generator<T> (private val random: Random) {
    companion object {
        /**
         * The constant MAX PERCENT.
         */
        const val MAX_PERCENT: Double = 100.0
    }

    private val keysMap: MutableMap<T, Pair<Double, Double>> = HashMap(32)

    /**
     * Generate key t.
     *
     * @return the t
     */
    fun generate(): T {
        return this.get(random.nextInt(MAX_PERCENT.toInt()) + 1.0)
    }

    /**
     * Gets key.
     *
     * @param random the random value
     * @return the key
     */
    fun get(random: Double): T {
        var randomVal = random
        for ((key, value) in this.convertToSimilarRanges()) {
            if (randomVal <= value) {
                return key
            }
            randomVal = Math.round((randomVal - value) * MAX_PERCENT) / MAX_PERCENT
        }
        throw RuntimeException("KeyGenerator is not configured correctly.")
    }

    private fun convertToSimilarRanges(): Set<AbstractMap.SimpleEntry<T, Double>> {
        return keysMap.entries.stream().map { (key, value): MutableMap.MutableEntry<T, Pair<Double, Double>> ->
                AbstractMap.SimpleEntry(key, value.first / value.second * 100)
        }.collect(Collectors.toSet())
    }

    /**
     * Add key generator.
     *
     * @param key    the key
     * @param chance the chance
     * @param range  the range
     * @return the key generator
     */
    @JvmOverloads
    fun add(key: T, chance: Double, range: Double = 100.0): Generator<T> {
        if (chance > range) {
            throw RuntimeException("the chance can't be greater than range")
        }
        keysMap[key] = Pair(chance, range)
        return this
    }

    /**
     * Add all key generators.
     *
     * @param map the map
     * @return the key generator
     */
    fun addAll(map: Map<out T, Pair<Double, Double>>): Generator<T> {
        keysMap.putAll(map)
        return this
    }

    /**
     * Remove key generator.
     *
     * @param key the key
     * @return the key generator
     */
    fun remove(key: T): Generator<T> {
        keysMap.remove(key)
        return this
    }

    /**
     * Clear key generator.
     *
     * @return the key generator
     */
    fun clear(): Generator<T> {
        keysMap.clear()
        return this
    }
}
