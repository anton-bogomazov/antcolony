package com.abogomazov.antsim.domain.world.objects

import com.abogomazov.antsim.domain.hex.CubeCoordinate

sealed class ImmovableWorldObject(hex: CubeCoordinate) : WorldObject(hex)