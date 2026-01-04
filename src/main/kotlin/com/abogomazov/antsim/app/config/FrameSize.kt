package com.abogomazov.antsim.app.config

data class FrameSize(
    val width: Int,
    val height: Int
) {
    init {
        require(width > 0 && height > 0) {}
    }
}