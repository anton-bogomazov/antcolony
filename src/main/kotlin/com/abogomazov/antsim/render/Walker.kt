package com.abogomazov.antsim.render

import com.abogomazov.antsim.app.RenderingContext
import com.abogomazov.antsim.domain.Walker
import com.abogomazov.antsim.domain.WalkerAnt
import kotlin.math.cos
import kotlin.math.sin

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

context(context: RenderingContext)
fun WalkerAnt.render(): RenderShape {
    val size = context.hexSize * WALKER_SIZE
    val arrow = RenderShape(
        listOf(
            Point(x = size, y = 0.0),
            Point(-size * 0.5, y = size * 0.5),
            Point(-size * 0.5, y = -size * 0.5)
        )
    ).translate(
        origin = hex.center(context.hexSize, context.origin),
        angleRad = direction.radian()
    )

    // если муравей несет еду — добавляем маленький кружок в центре стрелки
    val foodDot = if (carryingFood) {
        val center = hex.center(context.hexSize, context.origin)
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
