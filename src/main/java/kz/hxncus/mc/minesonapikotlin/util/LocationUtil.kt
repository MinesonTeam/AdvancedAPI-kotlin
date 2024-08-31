package kz.hxncus.mc.minesonapikotlin.util

import kz.hxncus.mc.minesonapikotlin.random.SimpleRandom
import org.bukkit.Axis
import org.bukkit.Location
import org.bukkit.World

/**
 * Gets random location.
 *
 * @param world      the world
 * @param min        the min
 * @param max        the max
 * @param axis       the axis
 * @return the random location
 */
fun getRandomLocation(world: World, min: Int, max: Int, axis: Axis): Location {
    return when (axis) {
        Axis.X -> getRandomLocation(world, Triple(min, 0, 0), Triple(max, 0, 0))
        Axis.Y -> getRandomLocation(world, Triple(0, min, 0), Triple(0, max, 0))
        Axis.Z -> getRandomLocation(world, Triple(0, 0, min), Triple(0, 0, max))
    }
}

/**
 * Gets random location.
 *
 * @param world the world
 * @param minXYZ  the min x and min y and min z
 * @param maxXYZ  the max x and max y and max z
 * @return the random location
 */
fun getRandomLocation(world: World, minXYZ: Triple<Int, Int, Int>, maxXYZ: Triple<Int, Int, Int>): Location {
    val random: SimpleRandom = SimpleRandom.get()
    val x: Int = random.nextInt(maxXYZ.first - minXYZ.first)
    val y: Int = random.nextInt(maxXYZ.second - minXYZ.second)
    val z: Int = random.nextInt(maxXYZ.third - minXYZ.third)
    return Location(world, x.toDouble(), y.toDouble(), z.toDouble())
}

/**
 * Gets random location.
 *
 * @param world the world
 * @param minXZ  the min x and min z
 * @param maxXZ  the max x and max z
 * @return the random location
 */
fun getRandomLocation(world: World, minXZ: Pair<Int, Int>, maxXZ: Pair<Int, Int>): Location {
    return getRandomLocation(world, Triple(minXZ.first, 0, minXZ.second), Triple(maxXZ.first, 0, maxXZ.second))
}
