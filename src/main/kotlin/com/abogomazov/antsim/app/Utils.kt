package com.abogomazov.antsim.app

import java.awt.Component
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun runFrame(component: Component) {
    SwingUtilities.invokeLater {
        val frame = JFrame("Hex Grid")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(800, 800)

        frame.add(component)

        frame.isVisible = true
    }
}