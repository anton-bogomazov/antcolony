package com.abogomazov.antsim.app

import com.abogomazov.antsim.domain.Direction
import com.abogomazov.antsim.domain.Grid
import com.abogomazov.antsim.domain.Hex
import com.abogomazov.antsim.domain.Simulation
import com.abogomazov.antsim.domain.Walker
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val simulation = Simulation(
        Grid(radius = 4u),
        listOf(
            Walker(Hex(0, 0, 0), Direction.NE),
            Walker(Hex(-2, 2, 0), Direction.W)
        ),
        tickFrame = 500.milliseconds,
    )

    runFrame(SimulatorPanel(simulation))
}

