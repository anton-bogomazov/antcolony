package com.abogomazov.antsim.domain

import kotlin.time.Duration

data class Simulation(
    val grid: Grid,
    val walkers: List<Walker>,
    val tickRate: Duration,
) {
    fun tick() {
        for (walker in walkers) {
            walker.step(grid)
        }
    }
}