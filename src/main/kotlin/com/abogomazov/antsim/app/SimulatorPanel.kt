package com.abogomazov.antsim.app

import com.abogomazov.antsim.domain.Simulation
import com.abogomazov.antsim.render.*
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JPanel
import javax.swing.Timer

class SimulatorPanel(
    private val hexSize: Double,
    private val simulation: Simulation,
) : JPanel() {
    init {
        val timer = Timer(simulation.tickRate.inWholeMilliseconds.toInt()) {
            simulation.tick()
            repaint()
        }
        timer.start()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        )
        val origin = Point(width / 2.0, height / 2.0)

        g2.drawShapes(origin, *simulation.world.grid.render(hexSize, origin).toTypedArray())
        g2.drawShapes(origin, *simulation.world.walkers.map { it.render(hexSize, origin) }.toTypedArray())
        g2.drawShapes(origin, *simulation.world.objects.map { it.render(hexSize, origin) }.toTypedArray())
    }

    // TODO use origin bound context
    private fun Graphics2D.drawShapes(origin: Point, vararg shapes: RenderShape) {
        shapes.forEach { shape ->
            val xs = shape.points.map { it.x.toInt() }.toIntArray()
            val ys = shape.points.map { it.y.toInt() }.toIntArray()

            color = shape.color.toAwtColor()
            fillPolygon(xs, ys, xs.size)

            // outline
            color = Color.BLACK.toAwtColor()
            drawPolygon(xs, ys, shape.points.size)
        }
    }
}