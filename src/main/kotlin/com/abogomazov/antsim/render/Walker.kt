package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.Walker

const val WALKER_SIZE = 0.5

fun Walker.render(
    hexSize: Double,
    origin: Point
): RenderShape {
    val size = hexSize * WALKER_SIZE
    val rightOrientedArrow = RenderShape(
        listOf(
            Point(x = size, y = 0.0),
            Point(-size * 0.5, size * 0.5),
            Point(-size * 0.5, -size * 0.5)
        )
    )
    return rightOrientedArrow.translate(
        origin = hex.center(hexSize, origin),
        angleRad = direction.radian(),
    )
}
