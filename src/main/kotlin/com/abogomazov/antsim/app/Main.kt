package com.abogomazov.antsim.app

import com.abogomazov.antsim.domain.*
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val grid = Grid(radius = 4u)
    val objects = listOf(
        Anthill(Hex(-1, 0, 1)),
        Anthill(Hex(0, 2, -2)),
        Food(Hex(-2, 4, -2), 15),
        Food(Hex(-4, 4, 0), 15),
        Obstacle(Hex(1, -2, 1)),
        Obstacle(Hex(2, -3, 1)),
        Obstacle(Hex(2, -4, 2)),
    )
    objects.forEach { grid.add(it) }

    val simulation = Simulation(
        grid,
        listOf(
            DummyWalker(Hex(0, 0, 0), Direction.NE),
            DummyWalker(Hex(-2, 2, 0), Direction.W)
        ),
        tickFrame = 500.milliseconds,
    )

    runFrame(SimulatorPanel(simulation))
}

