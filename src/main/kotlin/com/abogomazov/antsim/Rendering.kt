package com.abogomazov.antsim

import com.abogomazov.antsim.domain.Grid
import com.abogomazov.antsim.domain.Hex
import kotlin.math.sqrt

data class Point(val x: Double, val y: Double)

fun Hex.toPixel(
    hexSize: Double,
    origin: Point = Point(0.0, 0.0)
): Point {
    val px = hexSize * (sqrt(3.0) * x + sqrt(3.0) / 2 * z)
    val py = hexSize * (3.0 / 2 * z)
    return Point(px + origin.x, py + origin.y)
}

fun Grid.renderCenters(
    hexSize: Double,
    origin: Point = Point(0.0, 0.0)
): List<Pair<Hex, Point>> =
    cells.map { hex ->
        hex to hex.toPixel(hexSize, origin)
    }

fun Hex.polygon(
    hexSize: Double,
    origin: Point = Point(0.0, 0.0)
): List<Point> {
    val center = toPixel(hexSize, origin)

    return (0 until 6).map { i ->
        val angle = Math.PI / 180 * (60 * i - 30)
        Point(
            center.x + hexSize * Math.cos(angle),
            center.y + hexSize * Math.sin(angle)
        )
    }
}

fun Grid.renderPolygons(
    hexSize: Double,
    origin: Point = Point(0.0, 0.0)
): List<Pair<Hex, List<Point>>> =
    cells.map { hex ->
        hex to hex.polygon(hexSize, origin)
    }