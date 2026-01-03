package com.abogomazov.antsim.render.domain

data class RenderShape(
    val points: List<Point>,
    val color: Color,
) {
    fun rotatedAround(origin: Point, angleRad: Double): RenderShape =
        copy(points = points.map { origin + it.rotated(angleRad) })

    operator fun plus(renderShape: RenderShape): RenderShape =
        copy(points = points + renderShape.points)
}