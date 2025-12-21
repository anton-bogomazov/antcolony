package com.abogomazov.antsim.domain

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