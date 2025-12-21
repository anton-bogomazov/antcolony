package com.abogomazov.antsim.app

import java.awt.Component
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun runFrame(size: Pair<Int, Int>, component: Component) {
    val (w, h) = size
    SwingUtilities.invokeLater {
        val frame = JFrame("Hex Grid")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(w, h)

        frame.add(component)

        frame.isVisible = true
    }
}