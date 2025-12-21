package com.abogomazov.antsim.domain

import kotlin.time.Duration

data class Simulation(
    val world : World,
    val tickRate: Duration,
) {
    fun tick() {
        for (walker in world.walkers) {
            walker.step(world)
        }
    }
}