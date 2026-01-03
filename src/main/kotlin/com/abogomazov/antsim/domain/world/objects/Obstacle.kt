package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.hex.CubeCoordinate

class Obstacle(
    hex: CubeCoordinate,
) : ImmovableWorldObject(hex)