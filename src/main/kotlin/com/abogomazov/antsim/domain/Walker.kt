package com.abogomazov.antsim.domain

class Walker(
    initHex: Hex,
    initDir: Direction,
) {
    var hex: Hex = initHex
        private set

    var direction: Direction = initDir
        private set

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