package com.abogomazov.antsim.render

import com.abogomazov.antsim.app.RenderingContext
import com.abogomazov.antsim.domain.Walker

const val WALKER_SIZE = 0.5

context(context: RenderingContext)
fun Walker.render(): RenderShape {
    val size = context.hexSize * WALKER_SIZE
    val rightOrientedArrow = RenderShape(
        listOf(
            Point(x = size, y = 0.0),
            Point(-size * 0.5, size * 0.5),
            Point(-size * 0.5, -size * 0.5)
        )
    )
    return rightOrientedArrow.translate(
        origin = hex.center(context.hexSize, context.origin),
        angleRad = direction.radian(),
    )
}
