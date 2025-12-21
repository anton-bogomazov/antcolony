package com.abogomazov.antsim.render

data class Point(val x: Double, val y: Double) {
    operator fun plus(other: Point): Point =
        Point(x + other.x, y + other.y)
}
