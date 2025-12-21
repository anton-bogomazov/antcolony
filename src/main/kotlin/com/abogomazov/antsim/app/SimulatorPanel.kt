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
        with(
            Builder(
                g as Graphics2D,
                Point(width / 2.0, height / 2.0),
                hexSize = hexSize,
            )
        ) {
            drawShapes(*simulation.world.grid.render().toTypedArray())
            drawShapes(*simulation.world.walkers.map { it.render() }.toTypedArray())
            drawShapes(*simulation.world.objects.map { it.render() }.toTypedArray())
        }
    }
}

interface RenderingContext {
    val origin: Point
    val hexSize: Double
}

class Builder(
    private val canvas: Graphics2D,
    override val origin: Point,
    override val hexSize: Double,
) : RenderingContext {
    init {
        canvas.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        )
    }

    fun drawShapes(vararg shapes: RenderShape) {
        shapes.forEach { shape ->
            val xs = shape.points.map { it.x.toInt() }.toIntArray()
            val ys = shape.points.map { it.y.toInt() }.toIntArray()

            canvas.color = shape.color.toAwtColor()
            canvas.fillPolygon(xs, ys, xs.size)

            // outline
            canvas.color = Color.BLACK.toAwtColor()
            canvas.drawPolygon(xs, ys, shape.points.size)
        }
    }
}