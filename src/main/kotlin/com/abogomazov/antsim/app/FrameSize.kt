package com.abogomazov.antsim.app

data class FrameSize(
    val width: Int,
    val height: Int
) {
    init {
        require(width > 0 && height > 0) {}
    }
}