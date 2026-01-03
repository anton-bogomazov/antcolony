package com.abogomazov.antsim.render

import com.abogomazov.antsim.app.RenderingContext
import com.abogomazov.antsim.domain.hex.CubeCoordinate
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private const val HEX_SIDES = 6
private const val POINTY_TOP_ROTATION_RAD = -Math.PI / HEX_SIDES

private val VERTICAL_SPACING_FACTOR = 3.0 / 2.0
private val HORIZONTAL_SPACING_FACTOR = sqrt(3.0)
private val HORIZONTAL_OFFSET_FACTOR = HORIZONTAL_SPACING_FACTOR / 2.0

fun CubeCoordinate.center(
    hexSize: Double,
    origin: Point
): Point {
    val horizontalSpacing = hexSize * HORIZONTAL_SPACING_FACTOR
    val horizontalOffset = hexSize * HORIZONTAL_OFFSET_FACTOR
    val verticalSpacing = hexSize * VERTICAL_SPACING_FACTOR

    val px = horizontalSpacing * x + horizontalOffset * z + origin.x
    val py = verticalSpacing * z + origin.y

    return Point(px, py)
}

context(context: RenderingContext)
fun CubeCoordinate.polygon(color: Color): RenderShape {
    val center = center(context.hexSize, context.origin)

    val vertices = (0 until HEX_SIDES).map { i ->
        val angleRad = i * 2 * Math.PI / HEX_SIDES + POINTY_TOP_ROTATION_RAD
        Point(
            x = center.x + context.hexSize * cos(angleRad),
            y = center.y + context.hexSize * sin(angleRad)
        )
    }
    return RenderShape(vertices, color)
}
