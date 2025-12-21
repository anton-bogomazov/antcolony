package com.abogomazov.antsim.domain

data class Walker(
    var hex: Hex,
    var direction: Direction,
) {
    fun step(grid: Grid) {
        val nextHex = hex + direction.delta
        if (grid.contains(nextHex)) {
            hex = nextHex
        } else {
            direction = direction.cw()
            step(grid)
        }
    }
}