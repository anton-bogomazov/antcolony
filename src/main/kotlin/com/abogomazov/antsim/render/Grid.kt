package com.abogomazov.antsim.render

import com.abogomazov.antsim.render.domain.RenderingContext
import com.abogomazov.antsim.domain.grid.CubeCoordinate
import com.abogomazov.antsim.domain.grid.Grid
import com.abogomazov.antsim.domain.world.ObjectRegistry
import com.abogomazov.antsim.domain.world.objects.Pheromone
import com.abogomazov.antsim.render.domain.Color
import com.abogomazov.antsim.render.domain.Point
import com.abogomazov.antsim.render.domain.RenderShape
import kotlin.math.sqrt

context(context: RenderingContext)
fun Grid.render(registry: ObjectRegistry): List<RenderShape> =
    cells.map { hex ->
        val center = hex.center(context.hexRadius, context.origin)
        val pheromone = registry.objectsAt(hex).filterIsInstance<Pheromone>().singleOrNull()
        val color =
            if (pheromone != null) {
                Color.GREEN(pheromone.amount)
            } else {
                Color.WHITE
            }

        hex(context.hexRadius, color)
            .rotatedAround(center, 0.0)
    }

private const val VERTICAL_SPACING_FACTOR = 3.0 / 2.0
private val HORIZONTAL_SPACING_FACTOR = sqrt(3.0)
private val HORIZONTAL_OFFSET_FACTOR = HORIZONTAL_SPACING_FACTOR / 2.0

fun CubeCoordinate.center(hexSize: Double, origin: Point): Point {
    return Point(
        x = hexSize * (HORIZONTAL_SPACING_FACTOR * x + HORIZONTAL_OFFSET_FACTOR * z) + origin.x,
        y = hexSize * VERTICAL_SPACING_FACTOR * z + origin.y
    )
}