package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.Direction

fun Direction.radian(): Double =
    when (this) {
        Direction.E  -> 0.0
        Direction.NE -> -Math.PI / 3
        Direction.NW -> -2 * Math.PI / 3
        Direction.W  -> Math.PI
        Direction.SW -> 2 * Math.PI / 3
        Direction.SE -> Math.PI / 3
    }