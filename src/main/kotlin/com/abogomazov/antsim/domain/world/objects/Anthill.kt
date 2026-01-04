package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.grid.CubeCoordinate

class Anthill(
    position: CubeCoordinate,
    foodStored: UInt,
) : ImmovableWorldObject(position) {
    var foodStored: UInt = foodStored
        private set

    fun store(amount: UInt) {
        foodStored += amount
    }

    override fun toString(): String = "Anthill[$foodStored] at $hex"
}