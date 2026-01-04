package com.abogomazov.antsim.render

import com.abogomazov.antsim.render.domain.Color
import com.abogomazov.antsim.render.domain.Point
import com.abogomazov.antsim.render.domain.RenderShape
import kotlin.math.cos
import kotlin.math.sin

fun triangle(size: Double, color: Color): RenderShape =
    RenderShape(
        listOf(
            Point(0.0, -size),
            Point(-size, size),
            Point(size, size)
        ),
        color
    )

fun circle(radius: Double, color: Color, sides: Int = 16): RenderShape {
    val points = (0 until sides).map { i ->
        val angle = 2 * Math.PI * i / sides
        Point(
            radius * cos(angle),
            radius * sin(angle)
        )
    }
    return RenderShape(points, color)
}

fun square(size: Double, color: Color): RenderShape {
    val half = size / 2
    return RenderShape(
        listOf(
            Point(-half, -half),
            Point(half, -half),
            Point(half, half),
            Point(-half, half)
        ),
        color
    )
}

fun arrow(size: Double, color: Color): RenderShape {
    return RenderShape(
        listOf(
            Point(x = size, y = 0.0),
            Point(-size * 0.5, y = size * 0.5),
            Point(-size * 0.5, y = -size * 0.5)
        ),
        color = color
    )
}

private const val HEX_SIDES = 6
private const val POINTY_TOP_ROTATION_RAD = -Math.PI / HEX_SIDES

fun hex(radius: Double, color: Color): RenderShape {
    val vertices = (0 until HEX_SIDES).map { i ->
        val angleRad = i * 2 * Math.PI / HEX_SIDES + POINTY_TOP_ROTATION_RAD
        pointOnCircle(radius, angleRad)
    }
    return RenderShape(vertices, color)
}

fun pointOnCircle(radius: Double, angleRad: Double): Point =
    Point(
        x = radius * cos(angleRad),
        y = radius * sin(angleRad)
    )