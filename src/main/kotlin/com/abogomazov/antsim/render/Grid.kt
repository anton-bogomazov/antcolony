package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.Grid

fun Grid.renderPolygons(
    hexSize: Double,
    origin: Point,
): List<RenderShape> =
    cells.map { hex ->
        hex.polygon(hexSize, origin)
    }
