package com.abogomazov.antsim.app

import com.abogomazov.antsim.domain.grid.Direction
import com.abogomazov.antsim.domain.grid.Grid
import com.abogomazov.antsim.domain.world.ObjectRegistry
import com.abogomazov.antsim.domain.world.World
import com.abogomazov.antsim.domain.world.WorldSlicer
import com.abogomazov.antsim.domain.world.objects.Anthill
import com.abogomazov.antsim.domain.world.objects.Food
import com.abogomazov.antsim.domain.world.objects.WalkerAnt
import com.abogomazov.antsim.render.domain.Point
import com.abogomazov.antsim.render.render
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.Timer
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val parameters =
        Parameters(
            gridRadius = 1,
            frameSize = FrameSize(600, 600),
            tickRate = 500.milliseconds,
            world = WorldParameters(),
        )

    val grid = Grid(parameters.gridRadius.toUInt())
    val objects = listOf(
        Anthill(grid.cells.toList()[0], 0u),
        WalkerAnt(grid.cells.toList()[6], Direction.NW),
        Food(grid.cells.toList()[4], 2u),
    )
    val registry = ObjectRegistry(
        grid = grid,
        objects = objects.toSet(),
    )
    val slicer = WorldSlicer(grid, registry)
    val world = World(registry, slicer, parameters.world)

    SwingUtilities.invokeLater {
        val frame = JFrame("Hex Grid")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(parameters.frameSize.width, parameters.frameSize.height)

        frame.add(
            object : JPanel() {
                init {
                    val timer = Timer(parameters.tickRate.inWholeMilliseconds.toInt()) {
                        world.tick()
                        repaint()
                    }
                    timer.start()
                }

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
                        drawShapes(*registry.objects().render().toTypedArray())
                    }
                }
            }
        )

        frame.isVisible = true
    }
}