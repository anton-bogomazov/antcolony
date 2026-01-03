package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.grid.CubeCoordinate

class Anthill(
    position: CubeCoordinate,
    val foodStored: UInt,
) : ImmovableWorldObject(position) {
    override fun toString(): String = "Anthill${hashCode()}($foodStored)"
}