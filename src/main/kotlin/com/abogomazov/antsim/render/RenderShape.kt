package com.abogomazov.antsim.render

import kotlin.math.cos
import kotlin.math.sin

data class RenderShape(
    val points: List<Point>
) {
    fun translate(origin: Point, angleRad: Double): RenderShape {
        val cos = cos(angleRad)
        val sin = sin(angleRad)

        return RenderShape(
            points.map { p ->
                Point(
                    origin.x + p.x * cos - p.y * sin,
                    origin.y + p.x * sin + p.y * cos
                )
            }
        )
    }
}