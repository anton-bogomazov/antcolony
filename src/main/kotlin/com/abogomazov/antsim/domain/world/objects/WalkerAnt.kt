package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.hex.CubeCoordinate
import com.abogomazov.antsim.domain.hex.Direction
import com.abogomazov.antsim.domain.world.view.LocalView

class WalkerAnt private constructor(
    hex: CubeCoordinate,
    override var direction: Direction
) : Walker(hex) {
    companion object {
        fun spawnAt(anthill: Anthill): WalkerAnt =
            WalkerAnt(
                hex = anthill.hex,
                direction = Direction.entries.random()
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
                    Action.Move(next.relDirection),
                    Action.DepositPheromone(amount = 1.0),
                    Action.DropFood,
                )
            } else {
                listOf(
                    Action.Move(next.relDirection),
                    Action.DepositPheromone(amount = 1.0)
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
                val next = neighbors.random()
                listOf(
                    Action.Move(next.relDirection),
                )
            }
        }
    }
}