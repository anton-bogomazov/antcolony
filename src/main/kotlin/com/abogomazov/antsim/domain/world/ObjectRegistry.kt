package com.abogomazov.antsim.domain.world

import com.abogomazov.antsim.domain.grid.CubeCoordinate
import com.abogomazov.antsim.domain.grid.Grid
import com.abogomazov.antsim.domain.world.objects.ImmovableWorldObject
import com.abogomazov.antsim.domain.world.objects.WorldObject

class ObjectRegistry (
    private val grid: Grid,
    objects: Set<WorldObject>,
) {
    init {
        objects.forEach { obj ->
            require(obj.hex in grid.cells)
        }
    }

    private val objectMap: MutableMap<CubeCoordinate, MutableSet<WorldObject>> =
        objects.groupBy { it.hex }
            .mapValues { it.value.toMutableSet() }
            .toMutableMap()

    fun objects() = objectMap.flatMap { it.value }

    fun objectsAt(hex: CubeCoordinate): List<WorldObject> {
        require(hex in grid.cells)

        return objectMap[hex]?.toList().orEmpty()
    }

    fun add(obj: WorldObject) {
        require(obj.hex in grid.cells)

        objectMap.computeIfAbsent(obj.hex) { mutableSetOf() }.add(obj)
    }

    fun move(obj: WorldObject, to: CubeCoordinate) {
        require(obj !is ImmovableWorldObject) { "Can't move ImmovableWorldObject" }
        require(to in grid.cells)
        require(obj.hex in grid.cells)
        val objs = requireNotNull(objectMap[obj.hex])
        require(obj in objs)

        clear(obj)
        objectMap.computeIfAbsent(to) { mutableSetOf() }.add(obj)
    }

    fun clear(obj: WorldObject) {
        require(obj.hex in grid.cells)
        val objs = requireNotNull(objectMap[obj.hex])
        require(obj in objs)

        objs.remove(obj)
    }
}