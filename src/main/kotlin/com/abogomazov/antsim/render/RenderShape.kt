package com.abogomazov.antsim.render

import kotlin.math.cos
import kotlin.math.sin

data class RenderShape(
    val points: List<Point>,
    val color: Color = Color.WHITE,
) {
    fun translate(origin: Point, angleRad: Double): RenderShape {
        val cos = cos(angleRad)
        val sin = sin(angleRad)

        return copy(
            points = points.map { p ->
                Point(
                    origin.x + p.x * cos - p.y * sin,
                    origin.y + p.x * sin + p.y * cos
                )
            }
        )
    }
}

enum class Color {
    BLACK,
    WHITE,
    RED,
    BROWN,
}

fun Color.toAwtColor() =
    when (this) {
        Color.BLACK -> java.awt.Color.BLACK
        Color.WHITE -> java.awt.Color.WHITE
        Color.RED   -> java.awt.Color.RED
        Color.BROWN -> java.awt.Color(139, 69, 19)
    }