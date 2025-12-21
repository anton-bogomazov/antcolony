package com.abogomazov.antsim.domain

class Grid(
    private val radius: UInt,
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


    private val objects = mutableMapOf<Hex, WorldObject>()

    fun getObjects(): Set<WorldObject> = objects.values.toSet()

    fun add(obj: WorldObject) {
        require(obj.hex in cells)
        objects.computeIfAbsent(obj.hex) { obj }
    }

    fun clear(hex: Hex) {
        objects.remove(hex)
    }

    fun getObject(hex: Hex): WorldObject? {
        return objects[hex]
    }

    operator fun contains(hex: Hex): Boolean =
        hex in cells
}