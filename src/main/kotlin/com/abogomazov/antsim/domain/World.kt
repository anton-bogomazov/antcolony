package com.abogomazov.antsim.domain

// TODO check if at least 1 anthill exists
class World(
    val grid: Grid,
    // Walker is WorldObject
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

    val pheromones: MutableMap<Hex, Double> = mutableMapOf()

    fun evaporatePheromones(evaporationRate: Double = 0.05) {
        pheromones.keys.toList().forEach { hex ->
            pheromones[hex] = (pheromones[hex] ?: 0.0) * (1.0 - evaporationRate)
            if ((pheromones[hex] ?: 0.0) < 0.001) pheromones.remove(hex)
        }
    }

    fun depositPheromone(hex: Hex, amount: Double) {
        pheromones[hex] = (pheromones[hex] ?: 0.0) + amount
    }
}