package com.abogomazov.antsim.domain.world

import com.abogomazov.antsim.domain.grid.CubeCoordinate
import com.abogomazov.antsim.domain.grid.CubeVector
import com.abogomazov.antsim.domain.grid.Grid
import com.abogomazov.antsim.domain.grid.toDirectionOrNull
import com.abogomazov.antsim.domain.world.objects.*
import com.abogomazov.antsim.domain.world.view.CellSense
import com.abogomazov.antsim.domain.world.view.LocalView

class WorldSlicer(
    private val grid: Grid,
    private val registry: ObjectRegistry,
) {
    fun localViewFor(walker: Walker): LocalView =
        object : LocalView {
            override fun neighbors(): List<CellSense> =
                grid.neighborsOf(walker.hex).map { hex ->
                    val objs = registry.objectsAt(hex)
                    val relativeDirection =
                        CubeVector.between(walker.hex, hex)
                            .toDirectionOrNull() ?: error("Hex is not a neighbour")

                    CellSense(
                        relDirection = relativeDirection,
                        obstacle = objs.hasObstacle(),
                        food = objs.foodAmount(),
                        pheromone = 0.0, // TODO add pheromones
                        anthill = objs.hasAnthill(),
                        occupied = objs.isOccupied(),
                        homeSignal = computeHomeSignal(hex)
                    )
                }
        }

    private fun List<WorldObject>.hasObstacle() =
        any { it is Obstacle }

    private fun List<WorldObject>.hasAnthill() =
        any { it is Anthill }

    private fun List<WorldObject>.isOccupied(): Boolean =
        any { it is Walker }

    private fun List<WorldObject>.foodAmount() =
        filterIsInstance<Food>()
            .fold(0u) { acc, food -> acc + food.amount }

    private fun computeHomeSignal(hex: CubeCoordinate): Double {
        val anthills = registry.objects().filterIsInstance<Anthill>()
        return if (anthills.isEmpty()) 0.0
        else 1.0 / (anthills.minOf { it.hex.distance(hex) } + 1)
    }
}
