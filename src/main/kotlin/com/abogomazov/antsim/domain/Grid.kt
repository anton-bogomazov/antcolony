package com.abogomazov.antsim.domain

class Grid(
    private val radius: UInt
) {
    val cells: Set<Hex> = buildSet {
        val radius = radius.toInt()
        for (x in -radius..radius) {
            val yMin = maxOf(-radius, -x - radius)
            val yMax = minOf(radius, -x + radius)
            for (y in yMin..yMax) {
                val z = -x - y
                add(Hex(x, y, z))
            }
        }
    }

    operator fun contains(hex: Hex): Boolean =
        hex in cells
}