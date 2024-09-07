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
 * @return the uuid
 */
fun IntArray.toUuid(): UUID {
    return UUID(this[0].toLong() shl 32 or (this[1].toLong() and 4294967295L), this[2].toLong() shl 32 or (this[3].toLong() and 4294967295L))
}

/**
 * Uuid to int array int [ ].
 *
 * @return the int [ ]
 */
fun UUID.toIntArray(): IntArray {
    val l = this.mostSignificantBits
    val m = this.leastSignificantBits
    return l.leastMostToIntArray(m, true)
}

fun Long.leastMostToIntArray(uuidLong: Long, isMost: Boolean): IntArray {
    val firstInt = this.toInt()
    val firstIntShr = (this shr 32).toInt()
    val secInt = uuidLong.toInt()
    val secIntShr = (uuidLong shr 32).toInt()
    return if (isMost) {
        intArrayOf(firstIntShr, firstInt, secIntShr, secInt)
    } else {
        intArrayOf(secIntShr, secInt, firstIntShr, firstInt)
    }
}

/**
 * Uuid to byte array byte [ ].
 *
 * @return the byte [ ]
 */
fun UUID.toByteArray(): ByteArray {
    val byteArray = ByteArray(16)
    ByteBuffer.wrap(byteArray)
              .order(ByteOrder.BIG_ENDIAN)
              .putLong(this.mostSignificantBits)
              .putLong(this.leastSignificantBits)
    return byteArray
}
