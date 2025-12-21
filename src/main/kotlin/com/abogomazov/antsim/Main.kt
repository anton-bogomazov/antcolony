package com.abogomazov.antsim

import com.abogomazov.antsim.domain.Direction
import com.abogomazov.antsim.domain.Grid
import com.abogomazov.antsim.domain.Hex
import com.abogomazov.antsim.domain.Walker
import com.abogomazov.antsim.render.Point
import com.abogomazov.antsim.render.render
import com.abogomazov.antsim.render.renderPolygons
import java.awt.Color
import java.awt.Component
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val simulation = Simulation(
        Grid(radius = 4u),
        listOf(
            Walker(Hex(0, 0, 0), Direction.NE),
            Walker(Hex(-2, 2, 0), Direction.W)
        ),
        tickFrame = 500.milliseconds,
    )

    runFrame(SimulatorPanel(simulation))
}

fun runFrame(component: Component) {
    SwingUtilities.invokeLater {
        val frame = JFrame("Hex Grid")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(800, 800)

        frame.add(component)

        frame.isVisible = true
    }
}