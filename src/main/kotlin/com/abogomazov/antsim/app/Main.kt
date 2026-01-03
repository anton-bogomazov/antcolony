package com.abogomazov.antsim.app

import com.abogomazov.antsim.domain.hex.Grid
import com.abogomazov.antsim.domain.world.World
import kotlin.math.min
import kotlin.math.sqrt

fun main() {
    // pass as parameter
    val inputStream = object {}.javaClass.getResourceAsStream("/moderate-world.txt")
        ?: error("config is not found in resources")
    val params = parseSimulationParameters(inputStream)

    val grid = Grid(radius = params.worldSize)
    // FIXME validate if it has a few objects on the same hex
    val world = World(
        grid = grid,
        objects = params.objects.map { it.toDomain() }.toMutableSet()
    )

    runFrame(
        size = 1000 to 1000,
        SimulatorPanel(
            computeHexSize(params.worldSize, 1000, 1000), world)
    )
}

fun computeHexSize(radius: UInt, windowWidth: Int, windowHeight: Int): Double {
    val hexSizeX = windowWidth / (sqrt(3.0) * (2 * radius.toInt() + 1))
    val hexSizeY = windowHeight / (1.5 * (2 * radius.toInt() + 1))
    return min(hexSizeX, hexSizeY)
}

