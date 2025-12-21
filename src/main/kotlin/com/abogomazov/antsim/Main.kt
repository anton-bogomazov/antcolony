package com.abogomazov.antsim

import com.abogomazov.antsim.domain.Grid
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("Hex Grid")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(800, 800)

        frame.add(
            HexPanel(
                Grid(radius = 4u)
            )
        )

        frame.isVisible = true
    }
}

class HexPanel(
    private val grid: Grid
) : JPanel() {

    private val hexSize = 30.0

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        )

        val origin = Point(width / 2.0, height / 2.0)
        for ((_, points) in grid.renderPolygons(hexSize, origin)) {
            val xs = points.map { it.x.toInt() }.toIntArray()
            val ys = points.map { it.y.toInt() }.toIntArray()

            g2.color = Color(230, 230, 230)
            g2.fillPolygon(xs, ys, 6)

            g2.color = Color.DARK_GRAY
            g2.drawPolygon(xs, ys, 6)
        }


    }
}