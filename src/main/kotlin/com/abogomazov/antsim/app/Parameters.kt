package com.abogomazov.antsim.app

import kotlin.math.min
import kotlin.math.sqrt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

data class Parameters(
    val gridRadius: Int,
    val frameSize: FrameSize,
    val tickRate: Duration = 300.milliseconds,
    val world: WorldParameters,
) {
    val hexRenderRadius = optimalHexRadius(gridRadius, frameSize)

    private fun optimalHexRadius(
        gridRadius: Int,
        frameSize: FrameSize,
        indent: Int = 50
    ): Double {
        val width = frameSize.width - indent
        val height = frameSize.height - indent
        val size = 2 * gridRadius + 1

        return min(
            width / (sqrt(3.0) * size),
            height / (1.5 * size),
        )
    }
}

data class WorldParameters(
    val spawnChanceModifier: Double = 0.05,
    val evaporationRate: Double = 0.05,
)