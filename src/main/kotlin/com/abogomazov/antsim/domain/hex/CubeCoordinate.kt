package com.abogomazov.antsim.domain.hex

import kotlin.math.absoluteValue

data class CubeCoordinate(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    init {
        require(x + y + z == 0) { "Invalid cube coordinate" }
    }

    operator fun plus(other: CubeVector): CubeCoordinate =
        CubeCoordinate(x + other.dx, y + other.dy, z + other.dz)

    operator fun minus(other: CubeVector): CubeCoordinate =
        this + other.opposite()

    fun distance(other: CubeCoordinate): Int {
        return ((x - other.x).absoluteValue +
                (y - other.y).absoluteValue +
                (z - other.z).absoluteValue) / 2
    }

    override fun toString(): String = "($x, $y, $z)"
}
