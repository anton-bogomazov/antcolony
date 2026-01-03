package com.abogomazov.antsim.render.domain

import kotlin.math.cos
import kotlin.math.sin

data class Point(val x: Double, val y: Double) {
    operator fun plus(other: Point): Point =
        Point(x + other.x, y + other.y)

    fun rotated(angleRad: Double): Point {
        val cos = cos(angleRad)
        val sin = sin(angleRad)

        return Point(
            x * cos - y * sin,
            x * sin + y * cos
        )
    }
}