package com.abogomazov.antsim.domain

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
}
