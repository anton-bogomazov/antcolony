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

sealed class Color(val saturation: Double) {
    object BLACK : Color(1.0)
    object WHITE : Color(1.0)
    object RED : Color(1.0)
    object BROWN : Color(1.0)
    class GREEN(saturation: Double) : Color(saturation)
}

fun Color.toAwtColor() =
    when (this) {
        is Color.BLACK -> java.awt.Color.BLACK
        is Color.WHITE -> java.awt.Color.WHITE
        is Color.RED   -> java.awt.Color.RED
        is Color.BROWN -> java.awt.Color(139, 69, 19)
        is Color.GREEN -> java.awt.Color.getHSBColor(
            120f / 360f,   // Hue зеленого
            saturation.toFloat().coerceIn(0f, 1f), // Saturation
            1.0f           // Brightness
        )
    }