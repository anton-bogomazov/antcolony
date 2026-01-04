package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.grid.Direction
import com.abogomazov.antsim.domain.grid.CubeCoordinate
import com.abogomazov.antsim.domain.world.view.CellSense
import com.abogomazov.antsim.domain.world.view.LocalView
import kotlin.random.Random

class WalkerAnt(
    hex: CubeCoordinate,
    override var orientation: Direction
) : Walker(hex) {
    companion object {
        fun spawnAt(anthill: Anthill): WalkerAnt =
            WalkerAnt(
                hex = anthill.hex,
                orientation = Direction.entries.random()
            )
    }

    override var carryingFood: Boolean = false

    override fun decide(view: LocalView): List<Action> {
        val neighbors = view.neighbors().filterNot { it.obstacle || it.occupied }
        if (neighbors.isEmpty()) return listOf(Action.Idle)

        return if (carryingFood) {
            val next = neighbors.maxBy { it.homeSignal }
            if (next.anthill) {
                listOf(
                    Action.DepositPheromone(amount = 1.0),
                    Action.Move(next.relDirection),
                    Action.DropFood,
                )
            } else {
                listOf(
                    Action.DepositPheromone(amount = 1.0),
                    Action.Move(next.relDirection),
                )
            }
        } else {
            val foodCell = neighbors.firstOrNull { it.food > 0u }
            if (foodCell != null) {
                listOf(
                    Action.Move(foodCell.relDirection),
                    Action.PickFood,
                )
            } else {
                val cellsWithPheromones =
                    neighbors.filter { it.pheromone > 0.0 }
                var next = cellsWithPheromones.maxWithOrNull(
                    compareByDescending<CellSense> { it.pheromone }
                        .thenBy { it.homeSignal }
                ) ?: neighbors.random()

                // explore randomly
                if (Random.nextInt(0,10) > 8) {
                    next = neighbors.random()
                }

                listOf(
                    Action.Move(next.relDirection),
                )
            }
        }
    }
}