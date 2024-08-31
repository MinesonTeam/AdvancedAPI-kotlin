package kz.hxncus.mc.minesonapikotlin.util

import kotlin.math.sqrt

/**
 * Validates the range of coordinates.
 *
 * @param minX the minimum x-coordinate
 * @param maxX the maximum x-coordinate
 * @throws IllegalArgumentException if minX is greater than maxX
 */
fun validate(minX: Double, maxX: Double) {
    validate(listOf(minX), listOf(maxX))
}

/**
 * Validates the range of coordinates.
 *
 * @param minValues the minimum coordinates
 * @param maxValues the maximum coordinates
 * @throws IllegalArgumentException if any minimum value is greater than the corresponding maximum value
 */
fun validate(minValues: List<Double>, maxValues: List<Double>) {
    val size = minValues.size
    require(size == maxValues.size) { "Number of minimum values and maximum values must be equal" }
    for (i in 0 until size) {
        val min = minValues[i]
        val max = maxValues[i]
        require(!(min > max)) { "Minimum value $min is greater than maximum value $max. Index $i" }
    }
}

/**
 * Validates the range of coordinates.
 *
 * @param minX the minimum x-axis
 * @param minZ the minimum z-axis
 * @param maxX the maximum x-axis
 * @param maxZ the maximum z-axis
 * @throws IllegalArgumentException if minX is greater than maxX or minZ is greater than maxZ
 */
fun validate(minX: Double, minZ: Double, maxX: Double, maxZ: Double) {
    validate(listOf(minX, minZ), listOf(maxX, maxZ))
}

/**
 * Calculates the distance between two sets of coordinates.
 *
 * @param minXZ the xz-axis of the first point
 * @param maxXZ the xz-axis of the second point
 * @return the distance between the two points
 */
fun distance(minXZ: Pair<Double, Double>, maxXZ: Pair<Double, Double>): Double {
    return distance(Triple(minXZ.first, 0.0, minXZ.second), Triple(maxXZ.first, 0.0, maxXZ.second))
}

/**
 * Calculates the distance between three sets of coordinates.
 *
 * @param minXYZ the xyz-axis of the first point
 * @param maxXYZ the xyz-axis of the second point
 * @return the distance between the two points
 */
fun distance(minXYZ: Triple<Double, Double, Double>, maxXYZ: Triple<Double, Double, Double>): Double {
    validate(minXYZ, maxXYZ)
    val deltaX: Double = maxXYZ.first - minXYZ.first
    val deltaY: Double = maxXYZ.second - minXYZ.second
    val deltaZ: Double = maxXYZ.third - minXYZ.third
    return sqrt(StrictMath.pow(deltaX, 2.0) + StrictMath.pow(deltaY, 2.0) + StrictMath.pow(deltaZ, 2.0))
}

/**
 * Validates the range of coordinates.
 *
 * @param minXYZ the minimum xyz-axis
 * @param maxXYZ the maximum xyz-axis
 * @throws IllegalArgumentException if minX is greater than maxX, or minY is greater than maxY, or minZ is greater than maxZ
 */
fun validate(minXYZ: Triple<Double, Double, Double>, maxXYZ: Triple<Double, Double, Double>) {
    validate(listOf(minXYZ.first, minXYZ.second, minXYZ.third), listOf(maxXYZ.first, maxXYZ.second, maxXYZ.third))
}
