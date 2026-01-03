package com.abogomazov.antsim.render

import com.abogomazov.antsim.render.domain.RenderingContext
import com.abogomazov.antsim.domain.grid.CubeCoordinate
import com.abogomazov.antsim.domain.grid.Grid
import com.abogomazov.antsim.render.domain.Color
import com.abogomazov.antsim.render.domain.Point
import com.abogomazov.antsim.render.domain.RenderShape
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

context(_: RenderingContext)
fun Grid.render(): List<RenderShape> =
    cells.map { hex ->
        hex.hex(Color.WHITE)
    }

private const val HEX_SIDES = 6
private const val POINTY_TOP_ROTATION_RAD = -Math.PI / HEX_SIDES

context(context: RenderingContext)
fun CubeCoordinate.hex(color: Color): RenderShape {
    val center = center(context.hexRadius, context.origin)

    val vertices = (0 until HEX_SIDES).map { i ->
        val angleRad = i * 2 * Math.PI / HEX_SIDES + POINTY_TOP_ROTATION_RAD
        center + pointOnCircle(context.hexRadius, angleRad)
    }
    return RenderShape(vertices, color)
}

fun pointOnCircle(radius: Double, angleRad: Double): Point =
    Point(
        x = radius * cos(angleRad),
        y = radius * sin(angleRad)
    )

private const val VERTICAL_SPACING_FACTOR = 3.0 / 2.0
private val HORIZONTAL_SPACING_FACTOR = sqrt(3.0)
private val HORIZONTAL_OFFSET_FACTOR = HORIZONTAL_SPACING_FACTOR / 2.0

fun CubeCoordinate.center(hexSize: Double, origin: Point): Point {
    return Point(
        x = hexSize * (HORIZONTAL_SPACING_FACTOR * x + HORIZONTAL_OFFSET_FACTOR * z) + origin.x,
        y = hexSize * VERTICAL_SPACING_FACTOR * z + origin.y
    )
}