package com.abogomazov.antsim.app

import com.abogomazov.antsim.domain.Grid
import com.abogomazov.antsim.domain.Simulation
import com.abogomazov.antsim.domain.Walker
import com.abogomazov.antsim.render.Point
import com.abogomazov.antsim.render.render
import com.abogomazov.antsim.render.renderPolygons
import com.abogomazov.antsim.render.toAwtColor
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JPanel
import javax.swing.Timer

class SimulatorPanel(
    private val simulation: Simulation,
) : JPanel() {
    init {
        val timer = Timer(simulation.tickFrame.inWholeMilliseconds.toInt()) {
            simulation.tick()
            repaint()
        }
        timer.start()
    }

    companion object {
        private val hexSize = 30.0
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        )
        val origin = Point(width / 2.0, height / 2.0)

        g2.drawGrid(simulation.grid, origin)
        g2.drawWalkers(simulation.walkers, origin)
    }

    // TODO use origin bound context
    private fun Graphics2D.drawGrid(grid: Grid, origin: Point) {
        for (shape in grid.renderPolygons(hexSize, origin)) {
            val xs = shape.points.map { it.x.toInt() }.toIntArray()
            val ys = shape.points.map { it.y.toInt() }.toIntArray()

            color = shape.color.toAwtColor()
            fillPolygon(xs, ys, shape.points.size)

            // outline
            color = Color.DARK_GRAY
            drawPolygon(xs, ys, shape.points.size)
        }
    }

    private fun Graphics2D.drawWalkers(walkers: List<Walker>, origin: Point) {
        val shapes = walkers.map { it.render(hexSize, origin) }

        shapes.forEach { shape ->
            val xs = shape.points.map { it.x.toInt() }.toIntArray()
            val ys = shape.points.map { it.y.toInt() }.toIntArray()

            fillPolygon(xs, ys, xs.size)
        }
    }
}