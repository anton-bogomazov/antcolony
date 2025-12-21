package com.abogomazov.antsim.app

import com.abogomazov.antsim.domain.*
import com.abogomazov.antsim.parser.AxialCoordinate
import com.abogomazov.antsim.parser.SimulationParameters
import com.abogomazov.antsim.parser.WorldObjectDefinition
import com.abogomazov.antsim.parser.WorldObjectType
import com.abogomazov.antsim.parser.toDomain
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val params = SimulationParameters(
        worldSize = 16u,
        tickRate = 300.milliseconds,
        objects = listOf(
            WorldObjectDefinition(AxialCoordinate(q = -1, r = 1), type = WorldObjectType.ANTHILL),
            WorldObjectDefinition(AxialCoordinate(q = 0, r = -2), type = WorldObjectType.ANTHILL),
            WorldObjectDefinition(AxialCoordinate(q = -2, r = -2), amount = 15u, type = WorldObjectType.FOOD),
            WorldObjectDefinition(AxialCoordinate(q = -4, r = 0), amount = 15u, type = WorldObjectType.FOOD),
            WorldObjectDefinition(AxialCoordinate(q = 1, r = 1), type = WorldObjectType.OBSTACLE),
            WorldObjectDefinition(AxialCoordinate(q = 2, r = 1), type = WorldObjectType.OBSTACLE),
            WorldObjectDefinition(AxialCoordinate(q = 2, r = 2), type = WorldObjectType.OBSTACLE),
        )
    )

    val grid = Grid(radius = params.worldSize)
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

