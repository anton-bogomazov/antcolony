package com.abogomazov.antsim.app

import java.io.InputStream
import kotlin.time.Duration.Companion.milliseconds

fun parseSimulationParameters(inputStream: InputStream): SimulationParameters {
    val lines = inputStream.bufferedReader()
        .readLines()
        .map { it.trim() }
        .filter { it.isNotEmpty() && !it.startsWith("#") }

    if (lines.size < 2) {
        throw IllegalArgumentException("Config must contain at least worldSize and tickRate")
    }

    val worldSize = lines[0].toUInt()
    val tickRate = lines[1].toLong().milliseconds

    val objects = lines.drop(2).map { line ->
        val parts = line.split(",").map { it.trim() }
        val type = parts[0].uppercase()
        val q = parts.getOrNull(1)?.toInt()
            ?: error("Missing q coordinate for object $type")
        val r = parts.getOrNull(2)?.toInt()
            ?: error("Missing r coordinate for object $type")
        val amount = parts.getOrNull(3)?.toInt()

        WorldObjectDefinition(AxialCoordinate(r = r, q = q), WorldObjectType.valueOf(type), amount?.toUInt())
    }

    return SimulationParameters(
        worldSize = worldSize,
        tickRate = tickRate,
        objects = objects
    )
}