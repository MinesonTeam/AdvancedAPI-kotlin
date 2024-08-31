package kz.hxncus.mc.minesonapikotlin.color.caching

import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Class Lru cache.
 *
 * @author Hxncus
 * @since 1.0.0
 */
open class LruCache
/**
 * Instantiates a new Lru cache.
 *
 * @param maxSize the max size
 */(private val maxSize: Int) {
    private val deque: Deque<String> = LinkedList()

    private val map: MutableMap<String, LruElement> = ConcurrentHashMap(16)

    /**
     * Can equal boolean.
     *
     * @param other the other
     * @return the boolean
     */
    protected fun canEqual(other: Any?): Boolean {
        return other is LruCache
    }

    /**
     * Gets result.
     *
     * @param input the input
     * @return the result
     */
    fun getResult(input: String): String? {
        if (map.containsKey(input)) {
            val curr = map[input]
            synchronized(this.deque) {
                deque.remove(input)
                deque.addFirst(input)
            }
            return (curr ?: return null).result()
        }
        return null
    }

    /**
     * Put.
     *
     * @param input  the input
     * @param result the result
     */
    fun put(input: String, result: String) {
        synchronized(this.deque) {
            if (map.containsKey(input)) {
                deque.remove(input)
            } else {
                val size = deque.size
                if (size == this.maxSize && size > 0) {
                    val temp = deque.removeLast()
                    map.remove(temp)
                }
            }
            val newObj = LruElement(input, result)
            deque.addFirst(input)
            map.put(input, newObj)
        }
    }
}
