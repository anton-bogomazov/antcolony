package com.abogomazov.antsim.app

import com.abogomazov.antsim.domain.*
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    // pass as parameter
    val inputStream = object {}.javaClass.getResourceAsStream("/config.txt")
        ?: error("config.txt not found in resources")
    val params = parseSimulationParameters(inputStream)

    val grid = Grid(radius = params.worldSize)
    // FIXME validate if it has a few objects on the same hex
    params.objects.forEach { grid.add(it.toDomain()) }

    val simulation = Simulation(
        grid,
        listOf(
            DummyWalker(Hex(0, 0, 0), Direction.NE),
            DummyWalker(Hex(-2, 2, 0), Direction.W)
        ),
        tickRate = params.tickRate,
    )

    runFrame(
        size = 1000 to 1000,
        SimulatorPanel(
            computeHexSize(params.worldSize, 1000, 1000), simulation)
    )
}

fun computeHexSize(radius: UInt, windowWidth: Int, windowHeight: Int): Double {
    val hexSizeX = windowWidth / (sqrt(3.0) * (2 * radius.toInt() + 1))
    val hexSizeY = windowHeight / (1.5 * (2 * radius.toInt() + 1))
    return min(hexSizeX, hexSizeY)
}

