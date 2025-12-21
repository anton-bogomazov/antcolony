package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.Hex
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun Hex.toPixel(
    hexSize: Double,
    origin: Point = Point(0.0, 0.0)
): Point {
    val px = hexSize * (sqrt(3.0) * x + sqrt(3.0) / 2 * z)
    val py = hexSize * (3.0 / 2 * z)
    return Point(px + origin.x, py + origin.y)
}

fun Hex.polygon(
    hexSize: Double,
    origin: Point = Point(0.0, 0.0)
): List<Point> {
    val center = toPixel(hexSize, origin)

    return (0 until 6).map { i ->
        val angle = Math.PI / 180 * (60 * i - 30)
        Point(
            center.x + hexSize * cos(angle),
            center.y + hexSize * sin(angle)
        )
    }
}

fun Hex.centerPixel(
    hexSize: Double,
    origin: Point
): Point {
    val px = hexSize * (sqrt(3.0) * x + sqrt(3.0) / 2 * z)
    val py = hexSize * (3.0 / 2 * z)
    return Point(px + origin.x, py + origin.y)
}