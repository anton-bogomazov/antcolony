package com.abogomazov.antsim.app

import kotlin.math.min
import kotlin.math.sqrt

data class Parameters(
    val gridRadius: Int,
    val frameSize: FrameSize,
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