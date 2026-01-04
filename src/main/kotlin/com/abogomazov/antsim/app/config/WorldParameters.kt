package com.abogomazov.antsim.app.config

import com.abogomazov.antsim.domain.world.objects.WorldObject

data class WorldParameters(
    val initialObjects: Set<WorldObject>,
    val spawnChanceModifier: Double = 0.05,
    val evaporationRate: Double = 0.05,
)