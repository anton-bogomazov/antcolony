package com.abogomazov.antsim

import com.abogomazov.antsim.domain.Grid
import com.abogomazov.antsim.domain.Walker
import kotlin.time.Duration

data class Simulation(
    val grid: Grid,
    val walkers: List<Walker>,
    val tickFrame: Duration,
) {
    fun tick() {
        for (walker in walkers) {
            walker.step(grid)
        }
    }
}