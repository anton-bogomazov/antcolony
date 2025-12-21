package com.abogomazov.antsim.domain

import kotlin.math.absoluteValue

data class Hex(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    init {
        require(x + y + z == 0) { "Invalid cube coordinates" }
    }

    operator fun plus(other: Hex): Hex =
        Hex(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Hex): Hex =
        Hex(x - other.x, y - other.y, z - other.z)

    fun distance(other: Hex): Int {
        return ((x - other.x).absoluteValue +
                (y - other.y).absoluteValue +
                (z - other.z).absoluteValue) / 2
    }
}
