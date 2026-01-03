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

        return listOf(Action.Move(neighbors.random().relDirection))
    }
}