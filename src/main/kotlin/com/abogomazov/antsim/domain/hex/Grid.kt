package com.abogomazov.antsim.domain.hex

class Grid(
    private val radius: UInt,
) {
    val cells: Set<CubeCoordinate> = buildSet {
        val radius = radius.toInt()
        for (x in -radius..radius) {
            val yMin = maxOf(-radius, -x - radius)
            val yMax = minOf(radius, -x + radius)
            for (y in yMin..yMax) {
                val z = -x - y
                add(CubeCoordinate(x, y, z))
            }
        }
    }

    fun neighborsOf(coordinate: CubeCoordinate): List<CubeCoordinate> =
        Direction.entries
            .map { coordinate + it.toVector() }
            .filter(::contains)

    operator fun contains(coordinate: CubeCoordinate): Boolean =
        coordinate in cells
}