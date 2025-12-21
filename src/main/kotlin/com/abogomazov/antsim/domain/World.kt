package com.abogomazov.antsim.domain

class World(
    val grid: Grid,
    val walkers: Set<Walker>,
    objects: Set<WorldObject>,
) {
    private val objectMap = objects.associateBy { it.hex }.toMutableMap()

    val objects: Set<WorldObject>
        get() = objectMap.values.toSet()

    fun add(obj: WorldObject) {
        require(obj.hex in grid.cells)
        objectMap.computeIfAbsent(obj.hex) { obj }
    }

    fun clear(hex: Hex) {
        objectMap.remove(hex)
    }

    fun getObject(hex: Hex): WorldObject? {
        return objectMap[hex]
    }
}