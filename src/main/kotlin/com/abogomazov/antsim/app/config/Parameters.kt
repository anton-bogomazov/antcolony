package com.abogomazov.antsim.app.config

import com.abogomazov.antsim.domain.grid.CubeCoordinate
import com.abogomazov.antsim.domain.world.objects.Anthill
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

data class Parameters(
    val gridRadius: UInt,
    val frameSize: FrameSize,
    val tickRate: Duration,
    val world: WorldParameters,
) {
    val hexRenderRadius = optimalHexRadius(gridRadius, frameSize)

    companion object {
        val default = Parameters(
            gridRadius = 3u,
            frameSize = FrameSize(800, 800),
            tickRate = 300.milliseconds,
            world = WorldParameters(
                initialObjects = setOf(
                    Anthill(CubeCoordinate.ORIGIN, 0u)
                ),
                spawnChanceModifier = 0.05,
                evaporationRate = 0.1,
            )
        )
    }

    private fun optimalHexRadius(
        gridRadius: UInt,
        frameSize: FrameSize,
        indent: Int = 50
    ): Double {
        val width = frameSize.width - indent
        val height = frameSize.height - indent
        val size = 2 * gridRadius.toInt() + 1

        return min(
            width / (sqrt(3.0) * size),
            height / (1.5 * size),
        )
    }
}
