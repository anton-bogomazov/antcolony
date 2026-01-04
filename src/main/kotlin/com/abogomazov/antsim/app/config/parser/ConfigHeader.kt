package com.abogomazov.antsim.app.config.parser

data class ConfigHeader(
    val radius: Int,
    val tickRate: Long,
    val frameSize: Pair<Int, Int>,
    val spawnRate: Double,
    val evaporationRate: Double,
) {
    companion object {
        fun parse(lines: List<String>): ConfigHeader {
            val worldSize = lines[0].toInt()
            val frameSize = lines[1].split(",").map { it.trim() }.map { it.toInt() }
            val tickRate = lines[2].toLong()
            val params = lines[3].split(",").map { it.trim() }.map { it.toDouble() }

            return ConfigHeader(
                radius = worldSize,
                frameSize = frameSize[0] to frameSize[1],
                tickRate = tickRate,
                spawnRate = params[0],
                evaporationRate = params[1],
            )
        }
    }
}