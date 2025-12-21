package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.Direction
import com.abogomazov.antsim.domain.Grid
import com.abogomazov.antsim.domain.Hex
import com.abogomazov.antsim.domain.Walker
import kotlin.math.sqrt

data class Point(val x: Double, val y: Double)

data class RenderShape(
    val points: List<Point>
)

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


fun Direction.angleRad(): Double =
    when (this) {
        Direction.E  -> 0.0
        Direction.NE -> -Math.PI / 3
        Direction.NW -> -2 * Math.PI / 3
        Direction.W  -> Math.PI
        Direction.SW -> 2 * Math.PI / 3
        Direction.SE -> Math.PI / 3
    }

fun arrowShape(
    center: Point,
    angle: Double,
    size: Double
): List<Point> {

    // локальная форма (смотрит вправо)
    val local = listOf(
        Point(size, 0.0),
        Point(-size * 0.5, size * 0.5),
        Point(-size * 0.5, -size * 0.5)
    )

    val cos = Math.cos(angle)
    val sin = Math.sin(angle)

    return local.map { p ->
        Point(
            center.x + p.x * cos - p.y * sin,
            center.y + p.x * sin + p.y * cos
        )
    }
}

fun Hex.centerPixel(
    hexSize: Double,
    origin: Point
): Point {
    val px = hexSize * (Math.sqrt(3.0) * x + Math.sqrt(3.0) / 2 * z)
    val py = hexSize * (3.0 / 2 * z)
    return Point(px + origin.x, py + origin.y)
}

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