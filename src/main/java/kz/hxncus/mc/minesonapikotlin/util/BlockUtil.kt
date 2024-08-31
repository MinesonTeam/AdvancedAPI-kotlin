package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.block.Block
import org.bukkit.persistence.PersistentDataType

/**
 * The Radix.
 */
const val RADIX: Int = 16

/**
 * Is the player placed block?
 *
 * @param block the block
 * @return the boolean
 */
fun isPlayerPlaced(block: Block): Boolean {
    val hashCode = block.location.hashCode()
    val key = hashCode.toString(RADIX.coerceIn(2, 36))
    val namespacedKey = NamespacedKey(key)
    return block.chunk.persistentDataContainer.has(namespacedKey, PersistentDataType.INTEGER)
}