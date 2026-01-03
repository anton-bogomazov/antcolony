package com.abogomazov.antsim.render.domain

interface RenderingContext {
    val origin: Point
    val hexRadius: Double

    val walkerSize: Double get() = 0.5 * hexRadius
    val anthillSize: Double get() = 0.6 * hexRadius
    val foodSize: Double get() = 0.4 * hexRadius
    val obstacleSize: Double get() = 0.9 * hexRadius
}