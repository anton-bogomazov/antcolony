package com.abogomazov.antsim.domain.grid

import kotlin.math.absoluteValue

data class CubeCoordinate(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    init {
        require(x + y + z == 0) { "Invalid cube coordinate" }
    }

    companion object {
        val ORIGIN = CubeCoordinate(0, 0, 0)
    }

    operator fun plus(other: CubeVector): CubeCoordinate =
        CubeCoordinate(x + other.dx, y + other.dy, z + other.dz)

    operator fun minus(other: CubeVector): CubeCoordinate =
        this + other.opposite()

    fun distance(other: CubeCoordinate): Int {
        val vector = CubeVector.between(this, other)
        return maxOf(
            vector.dx.absoluteValue,
            vector.dy.absoluteValue,
            vector.dz.absoluteValue,
        )
    }

    override fun toString(): String = "($x, $y, $z)"
}
