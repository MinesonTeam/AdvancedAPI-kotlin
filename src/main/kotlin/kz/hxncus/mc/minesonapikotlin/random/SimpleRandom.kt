package kz.hxncus.mc.minesonapikotlin.random

import java.io.NotSerializableException
import java.io.Serial
import java.util.*
import java.util.random.RandomGenerator
import kotlin.math.min

/**
 * The type Simple random.
 * @author Hxncus
 * @since  1.0.0
 */
open class SimpleRandom : Random {
    companion object {
        @Serial
        private val serialVersionUID = 1L
        private val JUMP = longArrayOf(-2337365368286915419L, 1659688472399708668L)
        private val LONG_JUMP = longArrayOf(-3266927057705177477L, -2459076376072127807L)
        private var instance: SimpleRandom? = null

        /**
         * Get simple random.
         *
         * @return the simple random
         */
        fun get(): SimpleRandom {
            // Техника, которую мы здесь применяем, называется «блокировка с двойной
            // проверкой» (Double-Checked Locking). Она применяется, чтобы
            // предотвратить создание нескольких объектов-одиночек, если метод будет
            // вызван из нескольких потоков одновременно.
            //
            // Хотя переменная `result` вполне оправданно кажется здесь лишней, она
            // помогает избежать подводных камней реализации DCL в Java.
            //
            // Больше об этой проблеме можно почитать здесь:
            // https://ru.wikipedia.org/wiki/%D0%91%D0%BB%D0%BE%D0%BA%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B0_%D1%81_%D0%B4%D0%B2%D0%BE%D0%B9%D0%BD%D0%BE%D0%B9_%D0%BF%D1%80%D0%BE%D0%B2%D0%B5%D1%80%D0%BA%D0%BE%D0%B9
            val result = instance
            if (result != null) {
                return result
            }
            synchronized(SimpleRandom::class.java) {
                if (instance == null) {
                    instance = SimpleRandom()
                }
                return instance as SimpleRandom
            }
        }
    }

    private var s0: Long = 0
    private var s1: Long = 0

    /**
     * Instantiates a new Simple random.
     *
     * @param s0 the s 0
     * @param s1 the s 1
     */
    protected constructor(s0: Long, s1: Long) {
        this.s0 = s0
        this.s1 = s1
    }

    /**
     * Instantiates a new Simple random.
     *
     * @param seed the seed
     */
    protected constructor(seed: Long = RandomGenerator.getDefault().nextLong()) {
        this.setSeed(seed)
    }

    override fun setSeed(seed: Long) {
        val random: RandomGenerator = SplittableRandom(seed)
        this.s0 = random.nextLong()
        this.s1 = random.nextLong()
    }

    override fun nextBytes(bytes: ByteArray) {
        var i = bytes.size
        while (i != 0) {
            var n = min(i.toDouble(), 8.0).toInt()

            var bits = this.nextLong()
            while (n != 0) {
                n--
                --i
                bytes[i] = bits.toByte()
                bits = bits shr 8
            }
        }
    }

    override fun nextInt(): Int {
        return (this.nextLong() ushr 32).toInt()
    }

    /**
     * Next int int.
     *
     * @param l the l
     * @return the int
     */
    fun nextInt(l: Long): Int {
        return this.nextLong(l).toInt()
    }

    override fun nextLong(): Long {
        val s0 = this.s0
        var s1 = this.s1
        val result = s0 + s1
        s1 = s1 xor s0
        this.s0 = java.lang.Long.rotateLeft(s0, 24) xor s1 xor (s1 shl 16)
        this.s1 = java.lang.Long.rotateLeft(s1, 37)
        return result
    }

    override fun nextBoolean(): Boolean {
        return this.nextLong() < 0L
    }

    override fun nextFloat(): Float {
        return (this.nextLong() ushr 40) * 5.9604645E-8f
    }

    override fun nextDouble(): Double {
        return (this.nextLong() ushr 11) * 1.1102230246251565E-16
    }

    override fun nextInt(origin: Int, bound: Int): Int {
        return super.nextInt(bound - origin) + origin
    }

    override fun nextLong(bound: Long): Long {
        require(bound > 0L) { "illegal bound $bound (must be positive)" }
        var t = this.nextLong()
        val nMinus1 = bound - 1L
        if ((bound and nMinus1) == 0L) {
            return t ushr java.lang.Long.numberOfLeadingZeros(nMinus1) and nMinus1
        } else {
            var u = t ushr 1
            while (u + nMinus1 - ((u % bound).also { t = it }) < 0L) {
                u = this.nextLong() ushr 1
            }

            return t
        }
    }

    /**
     * Copy simple random.
     *
     * @return the simple random
     */
    fun copy(): SimpleRandom {
        return SimpleRandom(this.s0, this.s1)
    }

    /**
     * Next double fast double.
     *
     * @return the double
     */
    fun nextDoubleFast(): Double {
        return java.lang.Double.longBitsToDouble(4607182418800017408L or (this.nextLong() ushr 12)) - 1.0
    }

    /**
     * Jump simple random.
     *
     * @return the simple random
     */
    fun jump(): SimpleRandom {
        return this.jump(JUMP)
    }

    private fun jump(jump: LongArray): SimpleRandom {
        var s0 = 0L
        var s1 = 0L

        for (l in jump) {
            for (i in 0..63) {
                if ((l and (1L shl i)) != 0L) {
                    s0 = s0 xor this.s0
                    s1 = s1 xor this.s1
                }

                this.nextLong()
            }
        }

        this.s0 = s0
        this.s1 = s1
        return this
    }

    /**
     * Long jump simple random.
     *
     * @return the simple random
     */
    fun longJump(): SimpleRandom {
        return this.jump(LONG_JUMP)
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    fun setState(state: LongArray) {
        if (state.size == 2) {
            this.s0 = state[0]
            this.s1 = state[1]
        } else {
            throw IllegalArgumentException("The argument array contains " + state.size + " longs instead of " + 2)
        }
    }

    @Serial
    @Throws(ClassNotFoundException::class, NotSerializableException::class)
    private fun readObject() {
        throw NotSerializableException("kz.hxncus.mc.minesonapi.random.SimpleRandom")
    }

    @Serial
    @Throws(NotSerializableException::class)
    private fun writeObject() {
        throw NotSerializableException("kz.hxncus.mc.minesonapi.random.SimpleRandom")
    }
}
