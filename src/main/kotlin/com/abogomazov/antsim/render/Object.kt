package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.grid.Direction
import com.abogomazov.antsim.domain.world.objects.*
import com.abogomazov.antsim.render.domain.Color
import com.abogomazov.antsim.render.domain.Point
import com.abogomazov.antsim.render.domain.RenderShape
import com.abogomazov.antsim.render.domain.RenderingContext
import com.abogomazov.antsim.render.domain.radian
import kotlin.math.cos
import kotlin.math.sin

context(context: RenderingContext)
fun List<WorldObject>.render(): List<RenderShape> = map { it.render() }

context(context: RenderingContext)
fun WorldObject.render(): RenderShape {
    val center = hex.center(context.hexRadius, context.origin)

    return when (this) {
        is Anthill -> triangle(center, context.anthillSize, Color.BROWN)
        is Food -> circle(center, context.foodSize, Color.RED)
        is Obstacle -> square(center, context.obstacleSize, Color.BLACK)
        is Walker -> {
            val body = arrow(center, context.walkerSize, Color.BLACK, orientation)
            if (carryingFood) {
                body + circle(
                    center,
                    context.walkerSize * 0.3,
                    Color.BLACK
                )
            } else {
                body
            }
        }
    }
}

fun triangle(center: Point, size: Double, color: Color): RenderShape {
    val points = listOf(
        Point(center.x, center.y - size),       // вершина сверху
        Point(center.x - size, center.y + size),
        Point(center.x + size, center.y + size)
    )
    return RenderShape(points, color)
}

fun circle(center: Point, radius: Double, color: Color, sides: Int = 16): RenderShape {
    val points = (0 until sides).map { i ->
        val angle = 2 * Math.PI * i / sides
        Point(
            center.x + radius * cos(angle),
            center.y + radius * sin(angle)
        )
    }
    return RenderShape(points, color)
}

fun square(center: Point, size: Double, color: Color): RenderShape {
    val half = size / 2
    val points = listOf(
        Point(center.x - half, center.y - half),
        Point(center.x + half, center.y - half),
        Point(center.x + half, center.y + half),
        Point(center.x - half, center.y + half)
    )
    return RenderShape(points, color)
}

fun arrow(center: Point, size: Double, color: Color, orientation: Direction): RenderShape {
    return RenderShape(
        listOf(
            Point(x = size, y = 0.0),
            Point(-size * 0.5, y = size * 0.5),
            Point(-size * 0.5, y = -size * 0.5)
        ),
        color = color
    ).rotatedAround(
        origin = center,
        angleRad = orientation.radian()
    )
}
