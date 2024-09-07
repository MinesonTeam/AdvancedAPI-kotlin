package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

/**
 * The zero Vector.
 * Don't modify it.
 * If you want a modifiable one, create them.
 */
val ZERO_VECTOR: Vector = Vector()

/**
 * Gets min and max vector.
 *
 * @param vector the vector
 * @return the min and max vector
 */
fun Vector.getMinAndMaxVector(vector: Vector): Pair<Vector, Vector> {
    val boundingBox = BoundingBox.of(this, vector)
    return Pair(boundingBox.min, boundingBox.max)
}

/**
 * Gets min and max vector.
 *
 * @param x      the x
 * @param y      the y
 * @param z      the z
 * @return the min and max vector
 */
fun Vector.getMinAndMaxVector(x: Double, y: Double, z: Double): Pair<Vector, Vector> {
    val boundingBox = BoundingBox.of(this, x, y, z)
    return Pair(boundingBox.min, boundingBox.max)
}
