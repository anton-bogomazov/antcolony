package com.abogomazov.antsim.render

import com.abogomazov.antsim.app.RenderingContext
import com.abogomazov.antsim.domain.world.objects.Anthill
import com.abogomazov.antsim.domain.world.objects.Food
import com.abogomazov.antsim.domain.world.objects.WorldObject
import com.abogomazov.antsim.domain.world.objects.Obstacle
import com.abogomazov.antsim.domain.world.objects.Walker
import kotlin.math.cos
import kotlin.math.sin

context(context: RenderingContext)
fun WorldObject.render(): RenderShape {
    val center = hex.center(context.hexSize, context.origin)

    return when (this) {
        is Anthill -> triangle(center, context.hexSize * 0.6, Color.BROWN)
        is Food -> circle(center, context.hexSize * 0.4, Color.RED)
        is Obstacle -> square(center, context.hexSize * 0.7, Color.BLACK)
        is Walker -> arrow(center, context.hexSize * WALKER_SIZE, Color.BLACK)
    }
}

// TODO refactor
fun Walker.arrow(center: Point, size: Double, color: Color): RenderShape {
    val arrow = RenderShape(
        listOf(
            Point(x = size, y = 0.0),
            Point(-size * 0.5, y = size * 0.5),
            Point(-size * 0.5, y = -size * 0.5)
        ),
        color = color
    ).translate(
        origin = center,
        angleRad = direction.radian()
    )

    // если муравей несет еду — добавляем маленький кружок в центре стрелки
    val foodDot = if (carryingFood) {
        val dotRadius = size * 0.3
        val points = (0 until 12).map { i ->
            val angle = 2 * Math.PI * i / 12
            Point(
                center.x + dotRadius * cos(angle),
                center.y + dotRadius * sin(angle)
            )
        }
        RenderShape(points, color = Color.RED)
    } else null

    return if (foodDot != null) {
        // объединяем треугольник и кружок
        RenderShape(
            points = arrow.points + foodDot.points,
            color = arrow.color // основной цвет треугольника
        )
    } else arrow
}

private fun triangle(center: Point, size: Double, color: Color): RenderShape {
    val points = listOf(
        Point(center.x, center.y - size),       // вершина сверху
        Point(center.x - size, center.y + size),
        Point(center.x + size, center.y + size)
    )
    return RenderShape(points, color)
}

private fun circle(center: Point, radius: Double, color: Color, sides: Int = 12): RenderShape {
    val points = (0 until sides).map { i ->
        val angle = 2 * Math.PI * i / sides
        Point(
            center.x + radius * cos(angle),
            center.y + radius * sin(angle)
        )
    }
    return RenderShape(points, color)
}

private fun square(center: Point, size: Double, color: Color): RenderShape {
    val half = size / 2
    val points = listOf(
        Point(center.x - half, center.y - half),
        Point(center.x + half, center.y - half),
        Point(center.x + half, center.y + half),
        Point(center.x - half, center.y + half)
    )
    return RenderShape(points, color)
}