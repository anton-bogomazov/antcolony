package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.grid.CubeCoordinate

sealed class ImmovableWorldObject(hex: CubeCoordinate) : WorldObject(hex)