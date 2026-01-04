package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.grid.Direction
import com.abogomazov.antsim.domain.grid.CubeCoordinate
import com.abogomazov.antsim.domain.world.view.LocalView

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
                val next = neighbors.maxBy { it.pheromone }
                listOf(
                    Action.Move(next.relDirection),
                )
            }
        }
    }
}