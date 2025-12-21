package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.Anthill
import com.abogomazov.antsim.domain.Food
import com.abogomazov.antsim.domain.Object
import com.abogomazov.antsim.domain.Obstacle
import kotlin.math.cos
import kotlin.math.sin

fun Object.render(hexSize: Double, origin: Point): RenderShape {
    val center = hex.center(hexSize, origin)

    return when (this) {
        is Anthill -> triangle(center, hexSize * 0.6, Color.BROWN)
        is Food -> circle(center, hexSize * 0.4, Color.RED)
        is Obstacle -> square(center, hexSize * 0.7, Color.BLACK)
    }
}

private fun triangle(center: Point, size: Double, color: Color): RenderShape {
    val points = listOf(
        Point(center.x, center.y - size),       // вершина сверху
        Point(center.x - size, center.y + size),
        Point(center.x + size, center.y + size)
    )
    return RenderShape(points, color)
}

private fun circle(center: Point, radius: Double, color: Color, sides: Int = 12): RenderShape {
    val points = (0 until sides).map { i ->
        val angle = 2 * Math.PI * i / sides
        Point(
            center.x + radius * cos(angle),
            center.y + radius * sin(angle)
        )
    }
    return RenderShape(points, color)
}

private fun square(center: Point, size: Double, color: Color): RenderShape {
    val half = size / 2
    val points = listOf(
        Point(center.x - half, center.y - half),
        Point(center.x + half, center.y - half),
        Point(center.x + half, center.y + half),
        Point(center.x - half, center.y + half)
    )
    return RenderShape(points, color)
}