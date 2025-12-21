package com.abogomazov.antsim.render

import com.abogomazov.antsim.app.RenderingContext
import com.abogomazov.antsim.domain.Anthill
import com.abogomazov.antsim.domain.Food
import com.abogomazov.antsim.domain.Grid
import com.abogomazov.antsim.domain.Obstacle
import com.abogomazov.antsim.domain.World

context(_: RenderingContext)
fun World.render(): List<RenderShape> =
    grid.cells.map { hex ->
        val color = when (val obj = getObject(hex)) {
            is Anthill -> Color.WHITE
            is Food -> Color.WHITE
            is Obstacle -> Color.WHITE
            null -> {
                // пустая клетка: проверяем наличие феромона
                val pheromoneLevel = pheromones[hex] ?: 0.0
                if (pheromoneLevel > 0.0) Color.GREEN(pheromoneLevel.coerceIn(0.0, 1.0))
                else Color.WHITE
            }
        }
        // если феромонов много, делай цвет более насыщенным
        hex.polygon(color)
    }
