package com.abogomazov.antsim.render

import com.abogomazov.antsim.domain.Grid

fun Grid.render(
    hexSize: Double,
    origin: Point,
): List<RenderShape> =
    cells.map { hex ->
        hex.polygon(hexSize, origin)
    }
