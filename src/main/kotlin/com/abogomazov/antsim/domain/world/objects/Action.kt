package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.grid.Direction

sealed interface Action {
    data class Move(val direction: Direction) : Action
    data object PickFood : Action
    data object DropFood : Action
    data object Idle : Action
    data class DepositPheromone(val amount: Double) : Action
}