package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector
import kotlin.math.sqrt

class Vec3(var x: Double, var y: Double, var z: Double) {
    /**
     * Instantiates a new Vec 3.
     *
     * @param loc the loc
     */
    constructor(loc: Location) : this(loc.x, loc.y, loc.z)

    /**
     * Instantiates a new Vec 3.
     *
     * @param vec the vec
     */
    constructor(vec: Vector) : this(vec.x, vec.y, vec.z)

    /**
     * To vector vector.
     *
     * @return the vector
     */
    fun toVector(): Vector {
        return Vector(this.x, this.y, this.z)
    }

    /**
     * To location.
     *
     * @param world the world
     * @return the location
     */
    fun toLocation(world: World?): Location {
        return Location(world, this.x, this.y, this.z)
    }

    /**
     * To location.
     *
     * @param world the world
     * @param yaw   the yaw
     * @param pitch the pitch
     * @return the location
     */
    fun toLocation(world: World?, yaw: Float, pitch: Float): Location {
        return Location(world, this.x, this.y, this.z, yaw, pitch)
    }

    /**
     * Distance double.
     *
     * @param vec the vec
     * @return the double
     */
    fun distance(vec: Vec3): Double {
        return sqrt(this.pow2(this.x - vec.x) + this.pow2(this.y - vec.y) + this.pow2(this.z - vec.z))
    }

    private fun pow2(num: Double): Double {
        return num * num
    }

    /**
     * Norm vec 3.
     *
     * @return the vec 3
     */
    fun norm(): Vec3 {
        val length = this.length()
        this.x /= length
        this.y /= length
        this.z /= length
        return this
    }

    /**
     * Length double.
     *
     * @return the double
     */
    fun length(): Double {
        return sqrt(this.x * this.x + (this.y * this.y) + (this.z * this.z))
    }

    /**
     * Add vec 3.
     *
     * @param vec the vec
     * @return the vec 3
     */
    fun add(vec: Vec3): Vec3 {
        return this.add(vec.x, vec.y, vec.z)
    }

    /**
     * Add vec 3.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the vec 3
     */
    fun add(x: Double, y: Double, z: Double): Vec3 {
        this.x += x
        this.y += y
        this.z += z
        return this
    }

    /**
     * Subtract vec 3.
     *
     * @param vec the vec
     * @return the vec 3
     */
    fun subtract(vec: Vec3): Vec3 {
        return this.subtract(vec.x, vec.y, vec.z)
    }

    /**
     * Subtract vec 3.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the vec 3
     */
    fun subtract(x: Double, y: Double, z: Double): Vec3 {
        this.x -= x
        this.y -= y
        this.z -= z
        return this
    }

    /**
     * Divide vec 3.
     *
     * @param vec the vec
     * @return the vec 3
     */
    fun divide(vec: Vec3): Vec3 {
        return this.divide(vec.x, vec.y, vec.z)
    }

    /**
     * Divide vec 3.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the vec 3
     */
    fun divide(x: Double, y: Double, z: Double): Vec3 {
        this.x /= x
        this.y /= y
        this.z /= z
        return this
    }

    /**
     * Multiply vec 3.
     *
     * @param vec the vec
     * @return the vec 3
     */
    fun multiply(vec: Vec3): Vec3 {
        return this.multiply(vec.x, vec.y, vec.z)
    }

    /**
     * Multiply vec 3.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the vec 3
     */
    fun multiply(x: Double, y: Double, z: Double): Vec3 {
        this.x *= x
        this.y *= y
        this.z *= z
        return this
    }

    /**
     * Multiply vec 3.
     *
     * @param multiplier the multiplier
     * @return the vec 3
     */
    fun multiply(multiplier: Double): Vec3 {
        return this.multiply(multiplier, multiplier, multiplier)
    }

    /**
     * Angle double.
     *
     * @param vec the vec
     * @return the double
     */
    fun angle(vec: Vec3): Double {
        return StrictMath.acos(this.scalar(vec) / this.length() * vec.length())
    }

    /**
     * Scalar double.
     *
     * @param vec the vec
     * @return the double
     */
    fun scalar(vec: Vec3): Double {
        return this.x * vec.x + (this.y * vec.y) + (this.z * vec.z)
    }

    /**
     * Cross vec 3.
     *
     * @param vec the vec
     * @return the vec 3
     */
    fun cross(vec: Vec3): Vec3 {
        return Vec3(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x)
    }

    /**
     * Rotate z vec 3.
     *
     * @param angle the angle
     * @return the vec 3
     */
    fun rotateZ(angle: Double): Vec3 {
        val cos = StrictMath.cos(angle)
        val sin = StrictMath.sin(angle)
        return this.rotateZ(sin, cos)
    }

    /**
     * Rotate z vec 3.
     *
     * @param sin the sin
     * @param cos the cos
     * @return the vec 3
     */
    fun rotateZ(sin: Double, cos: Double): Vec3 {
        val prevX = this.x
        this.y = prevX * sin + this.y * cos
        this.x = prevX * cos - this.y * sin
        return this
    }

    /**
     * Rotate y vec 3.
     *
     * @param angle the angle
     * @return the vec 3
     */
    fun rotateY(angle: Double): Vec3 {
        val cos = StrictMath.cos(angle)
        val sin = StrictMath.sin(angle)
        return this.rotateY(sin, cos)
    }

    /**
     * Rotate y vec 3.
     *
     * @param sin the sin
     * @param cos the cos
     * @return the vec 3
     */
    fun rotateY(sin: Double, cos: Double): Vec3 {
        val prevX = this.x
        this.x = prevX * cos + this.z * sin
        this.z = this.z * cos - prevX * sin
        return this
    }

    /**
     * Rotate x vec 3.
     *
     * @param angle the angle
     * @return the vec 3
     */
    fun rotateX(angle: Double): Vec3 {
        val cos = StrictMath.cos(angle)
        val sin = StrictMath.sin(angle)
        return this.rotateX(sin, cos)
    }

    /**
     * Rotate x vec 3.
     *
     * @param sin the sin
     * @param cos the cos
     * @return the vec 3
     */
    fun rotateX(sin: Double, cos: Double): Vec3 {
        val prevY = this.y
        this.y = prevY * cos - this.z * sin
        this.z = prevY * sin + this.z * cos
        return this
    }
}
