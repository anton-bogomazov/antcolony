package com.abogomazov.antsim.domain.grid

class Grid(
    private val radius: UInt,
) {
    companion object {
        val MAX_SIZE = 100u
    }

    init {
        require(radius <= MAX_SIZE) { "Grid radius is too large; max size is $MAX_SIZE" }
        require(radius != 0u) { "Radius should be positive number; given zero" }
    }

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