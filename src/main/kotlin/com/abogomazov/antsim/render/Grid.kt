package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.Grid
import com.abogomazov.antsim.domain.Hex

fun Grid.renderPolygons(
    hexSize: Double,
    origin: Point = Point(0.0, 0.0)
): List<Pair<Hex, List<Point>>> =
    cells.map { hex ->
        hex to hex.polygon(hexSize, origin)
    }
