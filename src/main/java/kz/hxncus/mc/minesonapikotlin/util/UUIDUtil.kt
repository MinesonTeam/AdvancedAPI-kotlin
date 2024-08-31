package kz.hxncus.mc.minesonapikotlin.util

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*

/**
 * The constant EMPTY_UUID.
 */
val EMPTY_UUID: UUID = UUID(0, 0)

/**
 * Uuid from an int array uuid.
 *
 * @param array the array
 * @return the uuid
 */
fun uuidFromIntArray(array: IntArray): UUID {
    return UUID(
        array[0].toLong() shl 32 or (array[1].toLong() and 4294967295L),
        array[2].toLong() shl 32 or (array[3].toLong() and 4294967295L)
    )
}

/**
 * Uuid to int array int [ ].
 *
 * @param uuid the uuid
 * @return the int [ ]
 */
fun uuidToIntArray(uuid: UUID): IntArray {
    val l = uuid.mostSignificantBits
    val m = uuid.leastSignificantBits
    return leastMostToIntArray(l, m)
}

private fun leastMostToIntArray(uuidMost: Long, uuidLeast: Long): IntArray {
    return intArrayOf((uuidMost shr 32).toInt(), uuidMost.toInt(), (uuidLeast shr 32).toInt(), uuidLeast.toInt())
}

/**
 * Uuid to byte array byte [ ].
 *
 * @param uuid the uuid
 * @return the byte [ ]
 */
fun uuidToByteArray(uuid: UUID): ByteArray {
    val bs = ByteArray(16)
    ByteBuffer.wrap(bs)
        .order(ByteOrder.BIG_ENDIAN)
        .putLong(uuid.mostSignificantBits)
        .putLong(uuid.leastSignificantBits)
    return bs
}
