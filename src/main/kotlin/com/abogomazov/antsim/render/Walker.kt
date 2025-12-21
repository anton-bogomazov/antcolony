package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.Direction
import com.abogomazov.antsim.domain.Walker
import kotlin.math.cos
import kotlin.math.sin

fun Walker.render(
    hexSize: Double,
    origin: Point
): RenderShape {
    val center = hex.centerPixel(hexSize, origin)
    val angle = direction.angleRad()

    val points = arrowShape(
        center = center,
        angle = angle,
        size = hexSize * 0.5
    )

    return RenderShape(points)
}

fun arrowShape(
    center: Point,
    angle: Double,
    size: Double
): List<Point> {
    val local = listOf(
        Point(size, 0.0),
        Point(-size * 0.5, size * 0.5),
        Point(-size * 0.5, -size * 0.5)
    )

    val cos = cos(angle)
    val sin = sin(angle)

    return local.map { p ->
        Point(
            center.x + p.x * cos - p.y * sin,
            center.y + p.x * sin + p.y * cos
        )
    }
}

fun Direction.angleRad(): Double =
    when (this) {
        Direction.E  -> 0.0
        Direction.NE -> -Math.PI / 3
        Direction.NW -> -2 * Math.PI / 3
        Direction.W  -> Math.PI
        Direction.SW -> 2 * Math.PI / 3
        Direction.SE -> Math.PI / 3
    }
