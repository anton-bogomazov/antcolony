package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.hex.CubeCoordinate
import com.abogomazov.antsim.domain.hex.Direction
import com.abogomazov.antsim.domain.world.view.LocalView

sealed class Walker(hex: CubeCoordinate) : WorldObject(hex) {
    abstract val direction: Direction
    abstract var carryingFood: Boolean

    abstract fun decide(view: LocalView): List<Action>
}
