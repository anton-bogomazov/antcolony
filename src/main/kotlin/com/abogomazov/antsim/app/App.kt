package com.abogomazov.antsim.app

import com.abogomazov.antsim.domain.grid.Direction
import com.abogomazov.antsim.domain.grid.Grid
import com.abogomazov.antsim.domain.world.objects.Anthill
import com.abogomazov.antsim.domain.world.objects.Food
import com.abogomazov.antsim.domain.world.objects.Obstacle
import com.abogomazov.antsim.domain.world.objects.WalkerAnt
import com.abogomazov.antsim.render.domain.Point
import com.abogomazov.antsim.render.render
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

fun main() {
    val parameters =
        Parameters(
            gridRadius = 2,
            frameSize = FrameSize(600, 600),
        )

    val grid = Grid(parameters.gridRadius.toUInt())
    val objects = listOf(
        Anthill(grid.cells.toList()[0], 0u),
        Obstacle(grid.cells.toList()[1]),
        WalkerAnt(grid.cells.toList()[2], Direction.NW),
        WalkerAnt(grid.cells.toList()[3], Direction.SE).apply { carryingFood = true },
        Food(grid.cells.toList()[4], 15u),
    )

    SwingUtilities.invokeLater {
        val frame = JFrame("Hex Grid")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(parameters.frameSize.width, parameters.frameSize.height)

        frame.add(
            object : JPanel() {
                override fun paintComponent(g: Graphics) {
                    super.paintComponent(g)
                    with(
                        FrameBuilder(
                            g as Graphics2D,
                            Point(width / 2.0, height / 2.0),
                            parameters.hexRenderRadius,
                        )
                    ) {
                        drawShapes(*grid.render().toTypedArray())
                        drawShapes(*objects.render().toTypedArray())
                    }
                }
            }
        )

        frame.isVisible = true
    }
}