package com.abogomazov.antsim.render.domain

sealed class Color(val saturation: Double) {
    object BLACK : Color(1.0)
    object WHITE : Color(1.0)
    object RED : Color(1.0)
    object BROWN : Color(1.0)
    class GREEN(saturation: Double) : Color(saturation)
}