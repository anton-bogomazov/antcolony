package com.abogomazov.antsim.domain

sealed interface Walker {
    val hex: Hex
    val direction: Direction

    fun step(world: World)
}

