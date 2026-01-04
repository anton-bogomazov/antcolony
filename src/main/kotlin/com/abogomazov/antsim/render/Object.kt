package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.world.objects.*
import com.abogomazov.antsim.render.domain.*

context(context: RenderingContext)
fun Collection<WorldObject>.render(): List<RenderShape> = flatMap { it.render() }

context(context: RenderingContext)
fun WorldObject.render(): List<RenderShape> {
    val center = hex.center(context.hexRadius, context.origin)

    return when (this) {
        is Anthill -> listOf(triangle(context.anthillSize, Color.BROWN).rotatedAround(center, 0.0))
        is Food -> listOf(circle(context.foodSize, Color.RED).rotatedAround(center, 0.0))
        is Obstacle -> listOf(square(context.obstacleSize, Color.BLACK).rotatedAround(center, 0.0))
        is Walker -> {
            val body = arrow(context.walkerSize, Color.BLACK)
            if (carryingFood) {
                listOf(
                    body, circle(
                        context.walkerSize * 0.2,
                        Color.RED,
                    )
                )
            } else {
                listOf(body)
            }.map {
                it.rotatedAround(
                    origin = center,
                    angleRad = orientation.radian()
                )
            }
        }
    }
}
