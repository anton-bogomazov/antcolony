package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.grid.CubeCoordinate

class Obstacle(
    hex: CubeCoordinate,
) : ImmovableWorldObject(hex) {
    override fun toString(): String = "Obstacle at $hex"
}