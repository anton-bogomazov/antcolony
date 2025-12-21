package com.abogomazov.antsim.render

import com.abogomazov.antsim.app.RenderingContext
import com.abogomazov.antsim.domain.Grid

context(context: RenderingContext)
fun Grid.render(): List<RenderShape> =
    cells.map { hex ->
        hex.polygon()
    }
