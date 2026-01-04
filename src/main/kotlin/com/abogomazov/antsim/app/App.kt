package com.abogomazov.antsim.app

import com.abogomazov.antsim.app.config.parser.Config
import com.abogomazov.antsim.app.config.Parameters
import com.abogomazov.antsim.domain.grid.Grid
import com.abogomazov.antsim.domain.world.ObjectRegistry
import com.abogomazov.antsim.domain.world.World
import com.abogomazov.antsim.domain.world.WorldSlicer
import com.abogomazov.antsim.render.domain.Point
import com.abogomazov.antsim.render.render
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.Timer

class AntsimApp(
    private val parameters: Parameters,
) {
    private val grid = Grid(
        radius = parameters.gridRadius,
    )
    private val registry = ObjectRegistry(
        grid = grid,
        objects = parameters.world.initialObjects,
    )
    private val slicer = WorldSlicer(
        grid = grid,
        registry = registry,
    )

    private val world = World(
        registry = registry,
        slicer = slicer,
        params = parameters.world,
    )

    fun run() =
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

fun main() {
    val config = object {}.javaClass.getResourceAsStream("/small-world.txt")!!
    val parameters = Config.parse(config)
    AntsimApp(parameters).run()
}