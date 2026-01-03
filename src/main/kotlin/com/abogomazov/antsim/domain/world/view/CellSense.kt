package com.abogomazov.antsim.domain.world.view

import com.abogomazov.antsim.domain.grid.Direction

data class CellSense(
    val relDirection: Direction,
    val obstacle: Boolean,
    val food: UInt,
    val pheromone: Double,
    val anthill: Boolean,
    val occupied: Boolean,
    val homeSignal: Double,
)