package dev.thecodewarrior.kotlincpu.computer.util

import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt

class Vec2d(val x: Double, val y: Double) {
    val xf: Float get() = x.toFloat()
    val yf: Float get() = y.toFloat()
    val xi: Int get() = x.toInt()
    val yi: Int get() = y.toInt()

// Operations ==========================================================================================================

    fun floor(): Vec2d {
        return getPooled(floor(x), floor(y))
    }

    fun ceil(): Vec2d {
        return getPooled(ceil(x), ceil(y))
    }

    fun round(): Vec2d {
        return getPooled(round(x), round(y))
    }

    fun floorInt(): Vec2i {
        return Vec2i.getPooled(floorInt(x), floorInt(y))
    }

    fun ceilInt(): Vec2i {
        return Vec2i.getPooled(ceilInt(x), ceilInt(y))
    }

    fun roundInt(): Vec2i {
        return Vec2i.getPooled(roundInt(x), roundInt(y))
    }

    @JvmSynthetic
    operator fun plus(other: Vec2d): Vec2d = add(other)
    fun add(other: Vec2d): Vec2d {
        return getPooled(x + other.x, y + other.y)
    }

    fun add(otherX: Double, otherY: Double): Vec2d {
        return getPooled(x + otherX, y + otherY)
    }

    @JvmSynthetic
    operator fun minus(other: Vec2d): Vec2d = sub(other)
    fun sub(other: Vec2d): Vec2d {
        return getPooled(x - other.x, y - other.y)
    }

    fun sub(otherX: Double, otherY: Double): Vec2d {
        return getPooled(x - otherX, y - otherY)
    }

    @JvmSynthetic
    operator fun times(other: Vec2d): Vec2d = mul(other)
    fun mul(other: Vec2d): Vec2d {
        return getPooled(x * other.x, y * other.y)
    }

    fun mul(otherX: Double, otherY: Double): Vec2d {
        return getPooled(x * otherX, y * otherY)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline operator fun times(amount: Number): Vec2d = mul(amount.toDouble())
    fun mul(amount: Double): Vec2d {
        return getPooled(x * amount, y * amount)
    }

    @JvmSynthetic
    operator fun div(other: Vec2d): Vec2d = divide(other)
    fun divide(other: Vec2d): Vec2d {
        return getPooled(x / other.x, y / other.y)
    }

    fun divide(otherX: Double, otherY: Double): Vec2d {
        return getPooled(x / otherX, y / otherY)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline operator fun div(amount: Number): Vec2d = divide(amount.toDouble())
    fun divide(amount: Double): Vec2d {
        return getPooled(x / amount, y / amount)
    }

    infix fun dot(point: Vec2d): Double {
        return x * point.x + y * point.y
    }

    @JvmSynthetic
    operator fun unaryMinus(): Vec2d = this * -1

// Misc utils ==========================================================================================================

    @get:JvmName("lengthSquared")
    val lengthSquared: Double get() = x * x + y * y

    fun length(): Double {
        return sqrt(x * x + y * y)
    }

    fun normalize(): Vec2d {
        val norm = sqrt(x * x + y * y)
        return getPooled(x / norm, y / norm)
    }

    fun squareDist(vec: Vec2d): Double {
        val d0 = vec.x - x
        val d1 = vec.y - y
        return d0 * d0 + d1 * d1
    }

    fun projectOnTo(other: Vec2d): Vec2d {
        val norm = other.normalize()
        return norm.mul(this.dot(norm))
    }

    fun rotate(theta: Double): Vec2d {
        if (theta == 0.0) return this

        val cs = cos(theta)
        val sn = sin(theta)
        return getPooled(
            this.x * cs - this.y * sn,
            this.x * sn + this.y * cs
        )
    }

    /**
     * Run the passed function on the axes of this vector, then return a vector of the results.
     */
    inline fun map(fn: (Double) -> Double): Vec2d {
        return getPooled(fn(this.x), fn(this.y))
    }

    @JvmSynthetic
    operator fun component1(): Double = this.x
    @JvmSynthetic
    operator fun component2(): Double = this.y

//=============================================================================

    override fun toString(): String {
        return "($x,$y)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vec2d) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    companion object {

        @JvmField
        val ZERO = Vec2d(0.0, 0.0)

        private val poolBits = 7
        private val poolMask = (1 shl poolBits)-1
        private val poolMax = (1 shl poolBits -1)-1
        private val poolMin = -(1 shl poolBits -1)
        @JvmStatic
        private val pool = Array(1 shl poolBits *2) {
            val x = (it shr poolBits) + poolMin
            val y = (it and poolMask) + poolMin
            Vec2d(x.toDouble(), y.toDouble())
        }

        @JvmStatic
        fun getPooled(x: Double, y: Double): Vec2d {
            val xi = x.toInt()
            val yi = y.toInt()
            if (xi.toDouble() == x && xi in poolMin..poolMax &&
                yi.toDouble() == y && yi in poolMin..poolMax) {
//                AllocationTracker.vec2dPooledAllocations++
                return pool[
                    (xi- poolMin) shl poolBits or (yi- poolMin)
                ]
            }
            return Vec2d(x, y)
        }

        /**
         * Run the passed function on the axes of the passed vectors, then return a vector of the results.
         */
        inline fun zip(a: Vec2d, b: Vec2d, fn: (a: Double, b: Double) -> Double): Vec2d {
            return getPooled(fn(a.x, b.x), fn(a.y, b.y))
        }

        /**
         * Run the passed function on the axes of the passed vectors, then return a vector of the results.
         */
        inline fun zip(a: Vec2d, b: Vec2d, c: Vec2d, fn: (a: Double, b: Double, c: Double) -> Double): Vec2d {
            return getPooled(fn(a.x, b.x, c.x), fn(a.y, b.y, c.y))
        }

        /**
         * Run the passed function on the axes of the passed vectors, then return a vector of the results.
         */
        inline fun zip(a: Vec2d, b: Vec2d, c: Vec2d, d: Vec2d, fn: (a: Double, b: Double, c: Double, d: Double) -> Double): Vec2d {
            return getPooled(fn(a.x, b.x, c.x, d.x), fn(a.y, b.y, c.y, d.y))
        }
    }
}
