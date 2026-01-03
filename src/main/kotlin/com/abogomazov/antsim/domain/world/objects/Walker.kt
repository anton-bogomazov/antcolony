package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.grid.Direction
import com.abogomazov.antsim.domain.grid.CubeCoordinate
import com.abogomazov.antsim.domain.world.view.LocalView

sealed class Walker(hex: CubeCoordinate) : WorldObject(hex) {
    abstract var orientation: Direction
    abstract var carryingFood: Boolean

    abstract fun decide(view: LocalView): List<Action>

    override fun toString(): String = "Ant($carryingFood) at $hex"
}
