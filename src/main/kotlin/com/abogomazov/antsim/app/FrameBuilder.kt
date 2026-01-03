package com.abogomazov.antsim.app

import com.abogomazov.antsim.render.domain.Color
import com.abogomazov.antsim.render.domain.Point
import com.abogomazov.antsim.render.domain.RenderShape
import com.abogomazov.antsim.render.domain.RenderingContext
import java.awt.Graphics2D
import java.awt.RenderingHints

class FrameBuilder(
    private val canvas: Graphics2D,
    override val origin: Point,
    override val hexRadius: Double,
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

fun Color.toAwtColor() =
    when (this) {
        is Color.BLACK -> java.awt.Color.BLACK
        is Color.WHITE -> java.awt.Color.WHITE
        is Color.RED   -> java.awt.Color.RED
        is Color.BROWN -> java.awt.Color(139, 69, 19)
        is Color.GREEN -> java.awt.Color.getHSBColor(
            120f / 360f,
            saturation.toFloat().coerceIn(0f, 1f),
            1.0f
        )
    }